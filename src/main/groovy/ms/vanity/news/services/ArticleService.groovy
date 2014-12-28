package ms.vanity.news.services

import groovy.util.logging.Slf4j
import ms.vanity.news.commands.PopularityCommand
import ms.vanity.news.domains.Article
import ms.vanity.news.dto.PageableResult
import ms.vanity.news.dto.RankedEntity
import ms.vanity.news.repositories.ArticleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
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
    public PageableResult<Article> getNewest(final int page, final Integer size) {
        Page<Article> result = articleRepository.findAll(new PageRequest(page, size, Sort.Direction.DESC, 'dateCreated'))
        return new PageableResult(total: result.totalElements, page: result.content)
    }

    @Transactional(readOnly = true)
    public PageableResult<Article> getPopular(final Date from, final Integer page, final Integer size) {
        PageableResult<RankedEntity> popularities = new PopularityCommand(articlesPopularityUrl, from, page, size).execute()
        List<Article> articles = articleRepository.findAll(popularities.page*.id) as List
        new PageableResult(total: popularities.total, page: articles)
    }

}
