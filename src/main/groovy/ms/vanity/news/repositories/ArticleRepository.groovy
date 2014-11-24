package ms.vanity.news.repositories

import ms.vanity.news.domains.Article
import org.springframework.data.repository.PagingAndSortingRepository

interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
}
