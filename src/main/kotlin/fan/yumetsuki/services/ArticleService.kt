package fan.yumetsuki.services

import fan.yumetsuki.entities.Article
import fan.yumetsuki.entities.ArticleContent
import fan.yumetsuki.repos.ArticleRepository

interface ArticleService {
    suspend fun getArticlesPaged(page: Int? = null, pageSize: Int? = null): List<Article>
    suspend fun getArticleContentPaged(articleId: Int, page: Int? = null, pageSize: Int? = null): List<ArticleContent>
}

class ArticleServiceImpl(
    private val articleRepository: ArticleRepository
): ArticleService {

    override suspend fun getArticlesPaged(page: Int?, pageSize: Int?): List<Article> {
        return articleRepository.getArticlePaged(page, pageSize)
    }

    override suspend fun getArticleContentPaged(articleId: Int, page: Int?, pageSize: Int?): List<ArticleContent> {
        return articleRepository.getArticleContentPage(articleId, page, pageSize)
    }
}