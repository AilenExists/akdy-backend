package dev.shaper.akdymall.modules.route

import dev.shaper.akdymall.features.common.route.DefaultErrorResponse
import dev.shaper.akdymall.features.common.route.ErrorCode
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<BadRequestException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest,
                DefaultErrorResponse(cause.message ?: "Unknown error", ErrorCode.INVALID_REQUEST)
            )
        }
        exception<Throwable> { call, cause ->
            call.application.log.error("Unhandled", cause)
            call.respond(HttpStatusCode.InternalServerError,
                DefaultErrorResponse(cause.message ?: "Unknown error", ErrorCode.INTERNAL_SERVER_ERROR)
            )
        }
    }
}
