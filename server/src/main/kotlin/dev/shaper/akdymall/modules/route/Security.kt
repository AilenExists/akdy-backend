package dev.shaper.akdymall.modules.route

import dev.shaper.akdymall.features.auth.AuthService
import dev.shaper.akdymall.utils.ValueUtils.propertyGetter
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import org.koin.core.context.GlobalContext


fun Application.configureSecurity() {
//    val jwtAudience = "jwt-audience"
//    val jwtDomain = "http://0.0.0.0:8080/"
//    val jwtRealm = "ktor sample app"
//    val jwtSecret = "secret"
//    authentication {
//        jwt {
//            realm = jwtRealm
//            verifier(
//                JWT
//                    .require(Algorithm.HMAC256(jwtSecret))
//                    .withAudience(jwtAudience)
//                    .withIssuer(jwtDomain)
//                    .build()
//            )
//            validate { credential ->
//                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
//            }
//        }
//    }


    authentication {
        val application = this@configureSecurity
        val urlPath = application.propertyGetter("ktor.deployment.host") + application.propertyGetter("ktor.deployment.endpoint")
        oauth("auth-oauth-google") {
            urlProvider = { "${urlPath}/auth/oauth/google/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name            = "google",
                    authorizeUrl    = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl  = "https://accounts.google.com/o/oauth2/token",
                    requestMethod   = HttpMethod.Post,
                    clientId        = application.propertyGetter("oauth.google.id"),
                    clientSecret    = application.propertyGetter("oauth.google.secret"),
                    defaultScopes   = listOf("https://www.googleapis.com/auth/userinfo.profile"),
                    onStateCreated  = { call, state ->
                        // 로그인 하는 쪽에서 쿼리 요청시 ex) /auth/oauth/google/login?=redierct
                        val redirectTo = call.request.queryParameters["redirect"] ?: "/"
                        AuthService.oauth.create("oauth_state", redirectTo)
                    }
                )
            }
            client = GlobalContext.get().get<HttpClient>()
            fallback = { cause ->
                if(cause is OAuth2RedirectError)
                    respondRedirect("/auth/oauth/fallback")
                else
                    respond(HttpStatusCode.Forbidden,cause.message)
            }
        }
    }
}