package dev.shaper.akdymall.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.ucasoft.ktor.simpleCache.cacheOutput
import dev.shaper.akdymall.features.data.user.userRouting
import dev.shaper.akdymall.features.view.banner.bannerRouting
import io.ktor.http.HttpStatusCode
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds
import io.ktor.server.auth.*
import io.ktor.server.sessions.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.StatusPages

fun Application.configureRouting() {

    routing {
        route("/api/v1"){
            bannerRouting()
            userRouting()
        }
    }
}