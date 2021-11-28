package fan.yumetsuki.repos

import fan.yumetsuki.config.FileArticleConfig
import fan.yumetsuki.entities.Article
import fan.yumetsuki.entities.ArticleContent
import fan.yumetsuki.utils.readTextAsync
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File
import kotlin.math.ceil

interface ArticleRepository {
    suspend fun getArticlePaged(page: Int? = null, pageSize: Int? = null): List<Article>
    suspend fun getArticleContentPage(articleId: Int, page: Int? = null, pageSize: Int? = null): List<ArticleContent>
}

class FileArticleRepository(
    private val fileArticleConfig: FileArticleConfig
): ArticleRepository{

    companion object {
        const val NOT_CHANGE_CONTENT_FLAG = "\$content$"
    }

    override suspend fun getArticlePaged(page: Int?, pageSize: Int?): List<Article> = coroutineScope {
        val dir = File(fileArticleConfig.infoDirPath)
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return@coroutineScope emptyList()
            }
        }
        dir.listFiles { _, name ->
            name.endsWith(".json")
        }.limitPaged(
            page, pageSize
        ).map {
            it.readTextAsync()
        }.awaitAll().map {
            Json.decodeFromString(it)
        }
    }

    override suspend fun getArticleContentPage(articleId: Int, page: Int?, pageSize: Int?): List<ArticleContent> = coroutineScope {
        val dir = File(fileArticleConfig.contentDirPath)
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                return@coroutineScope emptyList()
            }
        }
        dir.listFiles { _, name ->
            name.endsWith(".json")
                && name.matches(Regex(".+-.+"))
                && name.removeSuffix(".json").split("-")
                    .lastOrNull()?.toIntOrNull()
                        ?.let { id -> id == articleId } ?: false
        }.firstOrNull()?.readTextAsync()?.await()?.let {
            Json.decodeFromString<List<ArticleContent>>(it)
        }?.map {
            it.readArticleContentAsync(dir)
        }?.limitPaged(page, pageSize)?.awaitAll() ?: emptyList()
    }

    private suspend fun ArticleContent.readArticleContentAsync(
        dir: File,
    ): Deferred<ArticleContent> = coroutineScope {
        async {
            content?.takeUnless {
                it == NOT_CHANGE_CONTENT_FLAG
            }?.let { articleFileName ->
                File(dir, articleFileName).takeIf {
                    it.exists()
                }?.let { contentFile ->
                    ArticleContent(chat, contentFile.readText())
                }
            } ?: this@readArticleContentAsync
        }
    }

    private fun <T> Array<T>.limitPaged(page: Int?, pageSize: Int?): List<T> {
        return this.toList().limitPaged(page, pageSize)
    }

    private fun <T> List<T>.limitPaged(page: Int?, pageSize: Int?): List<T> {
        return pageSize?.takeIf {
            it > 0
        }?.let {
            (page ?: 0) * pageSize
        }?.takeIf {
            it > 0
        }?.let {
            drop(it)
                .take(pageSize)
        } ?: this
    }

}