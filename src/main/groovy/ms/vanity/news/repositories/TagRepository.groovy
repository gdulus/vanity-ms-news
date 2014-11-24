package ms.vanity.news.repositories

import ms.vanity.news.domains.Tag
import ms.vanity.news.domains.TagStatus
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository

interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

    public List<Tag> findByStatus(TagStatus status, Pageable pageable)

}
