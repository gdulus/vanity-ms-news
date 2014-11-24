package ms.vanity.news.controllers

import ms.vanity.news.domains.Tag
import ms.vanity.news.domains.TagPopularity
import ms.vanity.news.services.TagService
import org.springframework.beans.factory.annotation.Autowired
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
    List<Tag> getPromoted(@RequestParam final Integer max) {
        return tagService.getPromoted(max)
    }

    @RequestMapping(value = "/popular", method = GET)
    List<TagPopularity> getPopular(@RequestParam final Integer dateOffset, @RequestParam final Integer max) {
        return tagService.getPopular(dateOffset, max)
    }

}
