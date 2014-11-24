package ms.vanity.news.controllers

import ms.vanity.news.domains.Article
import ms.vanity.news.services.ArticleService
import org.springframework.beans.factory.annotation.Autowired
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
    List<Article> getNewest(@RequestParam final Integer max) {
        return articleService.getNewest(max)
    }

    @RequestMapping(value = "/popular", method = GET)
    List<Article> getPopular(@RequestParam final Integer dateOffset, @RequestParam final Integer max) {
        return articleService.getPopular(dateOffset, max)
    }

}
