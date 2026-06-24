package dev.shaper.akdymall.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            cause.printStackTrace()
            call.respondText(
                text = "Error Occurred!: ${cause.stackTraceToString()}",
                status = HttpStatusCode.InternalServerError
            )
        }
    }
}
