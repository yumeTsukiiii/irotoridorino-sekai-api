package fan.yumetsuki.plugins

import fan.yumetsuki.config.repoModule
import fan.yumetsuki.config.serviceModule
import io.ktor.application.*
import org.koin.ktor.ext.Koin

fun Application.configurationKoin() {
    install(Koin) {
        modules(repoModule, serviceModule)
    }
}