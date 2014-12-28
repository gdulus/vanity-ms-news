package ms.vanity.news.controllers

import ms.vanity.news.domains.Tag
import ms.vanity.news.domains.TagPopularity
import ms.vanity.news.dto.PageableResult
import ms.vanity.news.services.TagService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping(value = "/tag")
class TagController {

    @Autowired
    TagService tagService

    @RequestMapping(value = "/promoted", method = GET)
    PageableResult<Tag> getPromoted(@RequestParam final Integer page,
                                    @RequestParam final Integer size) {
        return tagService.getPromoted(page, size)
    }

    @RequestMapping(value = "/popular", method = GET)
    PageableResult<TagPopularity> getPopular(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date from,
                                   @RequestParam final Integer page,
                                   @RequestParam final Integer size) {
        return tagService.getPopular(from, page, size)
    }

}
