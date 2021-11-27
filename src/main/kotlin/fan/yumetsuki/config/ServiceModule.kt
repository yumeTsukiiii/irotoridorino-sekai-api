package fan.yumetsuki.config

import fan.yumetsuki.services.ArticleService
import fan.yumetsuki.services.ArticleServiceImpl
import org.koin.dsl.module

val serviceModule = module {

    single<ArticleService> {
        ArticleServiceImpl(get())
    }

}