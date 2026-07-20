package dev.shaper.akdymall.features.auth

import dev.shaper.akdymall.features.data.user.FlatUser
import dev.shaper.akdymall.features.data.user.User
import dev.shaper.akdymall.features.data.user.UserRole
import dev.shaper.akdymall.features.data.user.UserService
import dev.shaper.akdymall.features.data.user.credential.CredentialProvider
import dev.shaper.akdymall.features.data.user.identity.FlatIdentity
import dev.shaper.akdymall.features.common.route.DefaultErrorResponse
import dev.shaper.akdymall.features.common.route.ErrorCode
import dev.shaper.akdymall.features.data.user.UserResponse
import dev.shaper.akdymall.features.data.user.credential.CredentialService
import dev.shaper.akdymall.services.clients.GoogleWrapper.fetchGoogleProfile
import io.ktor.http.*
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.mindrot.jbcrypt.BCrypt
import java.util.*

fun RoutingCall.addCookie(sessionId: String){
    response.cookies.append(
        name = "SID",
        value = sessionId,
        httpOnly = true,
        secure = false,           // 운영 (로컬 개발 땐 false)
        path = "/",
        maxAge = 1800
    )
}

suspend fun RoutingCall.loginProcess(user: User){
    val sessionId = AuthService.generateId()
    AuthService.session.create(
        id      = sessionId,
        value   = SessionData(user.userId,user.role.ordinal)
    )
    addCookie(sessionId)
    respond(
        HttpStatusCode.OK,
        UserResponse(
            user.username,
            user.email,
            user.phoneNumber,
            user.role,
            user.points,
            user.likes,
            user.profile,
        )
    )
}

fun Route.authRouting() {
    val userService: UserService by inject()
    val credentialService: CredentialService by inject()

    route("/auth") {
        /**
         * TODO
         * OAuth 2.0 회원가입 / 로그인 구현
         * 일반 로그인 / 회원가입 구현
         * ㄴ> BCrypt 해싱 및 해시 비교
         * 클라이언트 응답 방식 설정
         * Redis 정보 저장 설정
         * 차후 check-email / password-reset과 같은 계정 관리 시스템 추가
         * CHAPCA 시스템 도입으로 자동화 시스템 방어 -> 프론트 부탁해요!! (근데 나임)
         * */
        post("/login") {
            val req = call.receive<LoginRequest>()
            val user = userService.findUserByEmail(req.email)
                ?: return@post call.respond(
                    message = DefaultErrorResponse("Invalid email",ErrorCode.RESOURCE_NOT_FOUND),
                    status  = HttpStatusCode.Unauthorized
                )
            val credential = credentialService.findUserCredential(user.userId)
                ?: let {
                    call.respond(
                        message = DefaultErrorResponse("Cannot find Credential",ErrorCode.RESOURCE_CONFLICT),
                        status  = HttpStatusCode.InternalServerError
                    )
                    throw IllegalStateException("Credential not found : ${user.userId}")
                }
            val hashCheck = BCrypt.checkpw(req.password,credential.passwordHash)
            if (hashCheck) {
                call.loginProcess(user)
            } else call.respond(
                message = DefaultErrorResponse("Invalid password",ErrorCode.LOGIN_FAILED),
                status  = HttpStatusCode.Unauthorized
                )
        }

        post("/register") {
            val req = call.receive<RegisterRequest>()
            if(userService.findUserByEmail(req.email) != null)
                call.respond(
                    message = DefaultErrorResponse("User already exists", ErrorCode.DUPLICATE_EMAIL),
                    status  = HttpStatusCode.Conflict
                )
            else {
                val userUUID = UUID.randomUUID()
                val userData = FlatUser(
                    id          = userUUID,
                    email       = req.email,
                    username    = req.username,
                    phoneNumber = req.phoneNumber,
                    role        = UserRole.USER
                )
                val identityData = FlatIdentity(
                    userId  = userUUID,
                    zipCode = req.zipCode,
                    roadAddress = req.roadAddress,
                    detailAddress = req.detailAddress,
                    name = req.name,
                    age = req.age,
                    gender = req.gender,
                )
                userService.createUser(userData)
                credentialService.createUserIdentity(identityData)
                call.respond(HttpStatusCode.Accepted)
            }
        }

        post("/logout") {
            val cookie = call.request.cookies["SID"]
                ?: return@post call.respond(
                    message = DefaultErrorResponse("Invalid Session", ErrorCode.INVALID_TOKEN),
                    status  = HttpStatusCode.Unauthorized
                )
            val res = AuthService.session.delete(cookie)
            if(res)
                call.respond(HttpStatusCode.OK)
            else
                call.respond(
                    message = DefaultErrorResponse("User not found", ErrorCode.USER_NOT_FOUND),
                    status  = HttpStatusCode.Unauthorized
                )
        }

        route("/oauth") {
            route("/google") {
                authenticate("auth-oauth-google") {
                    get("/login") {
                        // Redirects to 'authorizeUrl'
                    }
                    get("/callback") {
                        val currentPrincipal = call.authentication.principal<OAuthAccessTokenResponse.OAuth2>()
                        val accessToken = currentPrincipal?.accessToken ?: return@get call.respond(HttpStatusCode.Unauthorized)
                        //login process
                        val profile = fetchGoogleProfile(accessToken)
                        val credential = credentialService.findUserCredentialByUserId(CredentialProvider.GOOGLE,profile.sub)
                            ?: return@get call.respond(HttpStatusCode.BadRequest,
                                DefaultErrorResponse(
                                    message = "use after linking local login",
                                    reason  = ErrorCode.INVALID_STATE_TRANSITION
                                )
                            )
                        val user = userService.findUserById(credential.userId)!!
                        call.loginProcess(user)
                        //redirect
                        currentPrincipal.state?.let { state ->
                            val redirectTo = AuthService.oauth.get<String>(state) ?: "/"
                            val safeRedirect = if (redirectTo.startsWith("/") && !redirectTo.startsWith("//"))
                                redirectTo else "/"
                            AuthService.oauth.delete(state)
                            call.respondRedirect(safeRedirect)
                        }
                    }
                }
            }
        }
    }
}