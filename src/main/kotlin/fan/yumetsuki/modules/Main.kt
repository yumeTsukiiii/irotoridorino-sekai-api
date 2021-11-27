package fan.yumetsuki.modules

import fan.yumetsuki.plugins.configurationKoin
import fan.yumetsuki.plugins.configureSerialization
import fan.yumetsuki.routes.configureRouting
import io.ktor.application.*

fun Application.main() {
    configureSerialization()
    configurationKoin()
    configureRouting()
}