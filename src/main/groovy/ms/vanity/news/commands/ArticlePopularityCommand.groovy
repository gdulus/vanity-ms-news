package ms.vanity.news.commands

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jcabi.http.Request
import com.jcabi.http.request.JdkRequest
import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey
import groovy.util.logging.Slf4j
import ms.vanity.news.domains.Popularity
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import java.lang.reflect.Type

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ArticlePopularityCommand extends HystrixCommand<List<Popularity>> {

    @Value('${ms.stats.popularity.article.url}')
    private String articlePopularityUrl

    @Value('${ms.stats.popularity.article.dateOffset}')
    private Integer dateOffset

    public ArticlePopularityCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("ms.news.article"));
    }

    @Override
    protected List<Popularity> run() {
        String body = new JdkRequest(articlePopularityUrl)
            .uri().queryParam('timestamp', (new Date() - dateOffset).time).back()
            .method(Request.GET)
            .fetch().body()

        Type listType = new TypeToken<ArrayList<Popularity>>() {}.getType();
        return new Gson().fromJson(body, listType)
    }

    @Override
    protected List<Popularity> getFallback() {
        return Collections.emptyList()
    }

}
