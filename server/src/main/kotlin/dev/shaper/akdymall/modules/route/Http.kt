package dev.shaper.akdymall.modules.route

import io.ktor.client.*
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.getKoin

fun Application.configureHttp() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
    val httpClient = HttpClient(OkHttp)
    {
        this@configureHttp.install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
    getKoin().declare<HttpClient>(httpClient)

}
