package ms.vanity.news.controllers

import ms.vanity.news.domains.Article
import ms.vanity.news.dto.PageableResult
import ms.vanity.news.services.ArticleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping(value = "/article")
class ArticleController {

    @Autowired
    private ArticleService articleService

    @RequestMapping(value = "/newest", method = GET)
    PageableResult<Article> getNewest(@RequestParam final Integer page,
                                      @RequestParam final Integer size) {
        return articleService.getNewest(page, size)
    }

    @RequestMapping(value = "/popular", method = GET)
    PageableResult getPopular(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date from,
                              @RequestParam final Integer page,
                              @RequestParam final Integer size) {
        return articleService.getPopular(from, page, size)
    }

}
