package ms.vanity.news.services

import groovy.util.logging.Slf4j
import ms.vanity.news.commands.PopularityCommand
import ms.vanity.news.domains.Popularity
import ms.vanity.news.domains.Tag
import ms.vanity.news.domains.TagPopularity
import ms.vanity.news.domains.TagStatus
import ms.vanity.news.repositories.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
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
    public List<Tag> getPromoted(final Integer max) {
        return tagRepository.findByStatus(TagStatus.PROMOTED, new PageRequest(0, max, Sort.Direction.ASC, 'name'))
    }

    @Transactional(readOnly = true)
    public List<TagPopularity> getPopular(final Integer dateOffset, final Integer max) {
        List<Popularity> popularities = new PopularityCommand(tagsPopularityUrl, dateOffset, max).execute()
        Integer maxRank = popularities*.rank.max()
        popularities.collect { new TagPopularity(tagRepository.findOne(it.id), it.rank, maxRank) } sort { it.tag.name }
    }

}
