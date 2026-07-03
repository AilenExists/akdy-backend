package dev.shaper.akdymall.plugins

import eu.vendeli.rethis.ReThis
import io.ktor.server.application.Application

fun Application.configureRedis() {

    val client = ReThis(
        host = environment.config.property("redis.url").getString()
    )

}