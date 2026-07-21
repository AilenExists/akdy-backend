package dev.shaper.akdymall.modules.route

import dev.shaper.akdymall.features.auth.authRouting
import dev.shaper.akdymall.features.data.product.productRouting
import dev.shaper.akdymall.features.data.review.reviewRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*
import dev.shaper.akdymall.features.data.user.userRouting
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.calllogging.CallLogging
import org.slf4j.event.Level

fun Application.configureRouting() {

    install(IgnoreTrailingSlash)
    install(CallLogging) {
        level = Level.INFO
    }

    routing {
        staticResources("/", "static")
        route("/api/v1"){
            authRouting()
            userRouting()
            productRouting()
            reviewRouting()
        }
    }
}