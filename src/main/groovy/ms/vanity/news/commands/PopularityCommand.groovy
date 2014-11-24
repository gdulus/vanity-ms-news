package ms.vanity.news.commands

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jcabi.http.Request
import com.jcabi.http.request.JdkRequest
import com.netflix.hystrix.HystrixCommand
import com.netflix.hystrix.HystrixCommandGroupKey
import ms.vanity.news.domains.Popularity

import java.lang.reflect.Type

public class PopularityCommand extends HystrixCommand<List<Popularity>> {

    private final String url

    private final int dateOffset

    private final int max

    public PopularityCommand(final String url, final int dateOffset, final int max) {
        super(HystrixCommandGroupKey.Factory.asKey("ms.news.popularity"));
        this.url = url
        this.dateOffset = dateOffset
        this.max = max
    }

    @Override
    protected List<Popularity> run() {
        String body = new JdkRequest(url)
            .uri()
            .queryParam('timestamp', (new Date() - dateOffset).time)
            .queryParam('max', max)
            .back()
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
