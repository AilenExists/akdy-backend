package dev.shaper.akdymall.features.data.user

import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.userRouting() {

    val userService: UserService by inject()
    route("/users"){

    }

}