package fan.yumetsuki.routes

import io.ktor.application.*
import io.ktor.routing.*

fun Application.apiRouting(build: Route.() -> Unit) {

    routing {
        route("/api", build)
    }

}

fun Application.configureRouting() {
    apiRouting {
        articleRoute()
    }
}