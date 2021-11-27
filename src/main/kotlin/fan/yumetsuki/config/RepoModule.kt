package fan.yumetsuki.config

import fan.yumetsuki.repos.ArticleRepository
import fan.yumetsuki.repos.FileArticleRepository
import org.koin.dsl.module

val repoModule = module {

    single {
        FileArticleConfig(
            "static/articles_info",
            "static/articles_content"
        )
    }

    single<ArticleRepository> {
        FileArticleRepository(get())
    }
}