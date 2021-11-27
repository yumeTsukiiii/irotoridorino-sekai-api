package fan.yumetsuki.routes

import fan.yumetsuki.domain.Article
import fan.yumetsuki.domain.ArticleContent
import fan.yumetsuki.services.ArticleService
import fan.yumetsuki.utils.mapThis
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.articleRoute() {

    val articleService: ArticleService by inject()

    get("/articles") {
        val page = call.request.queryParameters["page"]?.toIntOrNull()
        val pageSize = call.request.queryParameters["pageSize"]?.toIntOrNull()
        call.respond(
            articleService.getArticlesPaged(page, pageSize).mapThis {
                Article(id, title, description, createTime, updateTime, imgSrc)
            }
        )
    }

    get("/article/{articleId}") {
        val articleId = call.parameters["articleId"]?.toIntOrNull()
        val page = call.request.queryParameters["page"]?.toIntOrNull()
        val pageSize = call.request.queryParameters["pageSize"]?.toIntOrNull()
        articleId?.also {
            call.respond(articleService.getArticleContentPaged(
                it, page, pageSize
            ).mapThis {
                ArticleContent(chat, content)
            })
        } ?: call.respond(HttpStatusCode.NotFound)
    }

}