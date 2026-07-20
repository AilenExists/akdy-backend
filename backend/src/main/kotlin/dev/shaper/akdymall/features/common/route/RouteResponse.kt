package dev.shaper.akdymall.features.common.route

import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingCall

object RouteResponse {

    suspend fun RoutingCall.rejectKeyword() {
        respond(
            HttpStatusCode.BadRequest,
            DefaultErrorResponse("Keyword can't be null!",ErrorCode.MISSING_PARAMETER)
        )
    }
}