package dev.shaper.akdymall.features.auth

import dev.shaper.akdymall.features.data.user.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt

fun Route.authRoute() {
    route("/auth") {
        /**
         * TODO
         * OAuth 2.0 회원가입 / 로그인 구현
         * 일반 로그인 / 회원가입 구현
         * ㄴ> BCrypt 해싱 및 해시 비교
         * 클라이언트 응답 방식 설정
         * Redis 정보 저장 설정
         * */
        post("/login") {
            val userService: UserService by inject()
            val req = call.receive<LoginRequest>()
            val user = userService.findUserByEmail(req.email)
                ?: return@post call.respond(HttpStatusCode.Unauthorized)
            val credential = userService.findUserCredential(user.userId)
                ?: return@post call.respond(HttpStatusCode.Unauthorized)
            val hashCheck = BCrypt.checkpw(req.password,credential.passwordHash)
            if (hashCheck) {
                call.respond(HttpStatusCode.OK)

            }

        }
    }
}