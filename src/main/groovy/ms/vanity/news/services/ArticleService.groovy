package ms.vanity.news.services

import groovy.util.logging.Slf4j
import ms.vanity.news.commands.PopularityCommand
import ms.vanity.news.domains.Article
import ms.vanity.news.domains.Popularity
import ms.vanity.news.repositories.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
class ArticleService {

    @Autowired
    private ArticleRepository articleRepository

    @Value('${ms.stats.popularity.articles.url}')
    private String articlesPopularityUrl

    @Transactional(readOnly = true)
    public List<Article> getNewest(final Integer max) {
        return articleRepository.findAll(new PageRequest(0, max, Sort.Direction.DESC, 'dateCreated')).content
    }

    @Transactional(readOnly = true)
    public List<Article> getPopular(final Integer dateOffset, final Integer max) {
        List<Popularity> popularities = new PopularityCommand(articlesPopularityUrl, dateOffset, max).execute()
        articleRepository.findAll(popularities*.id) as List
    }

}
