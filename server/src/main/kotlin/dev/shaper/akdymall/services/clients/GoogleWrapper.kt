package dev.shaper.akdymall.services.clients

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object GoogleWrapper: KoinComponent {

    private val client: HttpClient by inject()

    suspend fun fetchGoogleProfile(token: String): GoogleUserInfo {
        return client.get("https://www.googleapis.com/oauth2/v3/userinfo") {
            headers.append(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }

}