package ms.vanity.news.commands

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jcabi.http.Request
import com.jcabi.http.request.JdkRequest
import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey
import groovy.util.logging.Slf4j
import ms.vanity.news.dto.PageableResult
import ms.vanity.news.dto.RankedEntity

import java.lang.reflect.Type

@Slf4j
public class PopularityCommand extends HystrixCommand<PageableResult<RankedEntity>> {

    private static final PageableResult<RankedEntity> EMPTY = new PageableResult<>(total: 0, page: Collections.emptyList())

    private final String url

    private final Date from

    private final Integer page

    private final Integer size

    public PopularityCommand(final String url, final Date from, final Integer page, final Integer size) {
        super(HystrixCommandGroupKey.Factory.asKey("ms.news.popularity"));
        this.url = url
        this.from = from
        this.page = page
        this.size = size
    }

    @Override
    protected PageableResult run() {
        String body = new JdkRequest(url)
            .uri()
            .queryParam('from', from.format('yyyy-MM-dd'))
            .queryParam('page', page)
            .queryParam('size', size)
            .back()
            .method(Request.GET)
            .fetch().body()

        Type listType = new TypeToken<PageableResult<RankedEntity>>() {}.getType();
        return new Gson().fromJson(body, listType)
    }

    @Override
    protected PageableResult getFallback() {
        log.error("Error executing call.", this.failedExecutionException)
        return EMPTY
    }

}
