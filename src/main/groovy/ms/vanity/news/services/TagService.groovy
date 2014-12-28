package ms.vanity.news.services

import groovy.util.logging.Slf4j
import ms.vanity.news.commands.PopularityCommand
import ms.vanity.news.domains.Tag
import ms.vanity.news.domains.TagPopularity
import ms.vanity.news.domains.TagStatus
import ms.vanity.news.dto.PageableResult
import ms.vanity.news.dto.RankedEntity
import ms.vanity.news.repositories.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
class TagService {

    @Autowired
    private TagRepository tagRepository

    @Value('${ms.stats.popularity.tags.url}')
    private String tagsPopularityUrl

    @Transactional(readOnly = true)
    public PageableResult<Tag> getPromoted(final int page, final Integer size) {
        Page<Tag> result = tagRepository.findByStatus(TagStatus.PROMOTED, new PageRequest(page, size, Sort.Direction.ASC, 'name'))
        return new PageableResult(total: result.totalElements, page: result.content)
    }

    @Transactional(readOnly = true)
    public PageableResult<TagPopularity> getPopular(final Date from, final Integer page, final Integer size) {
        PageableResult<RankedEntity> popularities = new PopularityCommand(tagsPopularityUrl, from, page, size).execute()
        Long maxRank = popularities.page.rank.max()
        List<TagPopularity> tags = popularities.page.collect { new TagPopularity(tagRepository.findOne(it.id), it.rank, maxRank) }
        return new PageableResult(total: popularities.total, page: tags)
    }

}
