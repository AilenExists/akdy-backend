package dev.shaper.akdymall.features.auth

import dev.shaper.akdymall.services.redis.RedisWrapper
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import java.security.SecureRandom
import java.util.Base64

@OptIn(ExperimentalLettuceCoroutinesApi::class)
object AuthService: KoinComponent {

    private val secureRandom = SecureRandom()

    val session: RedisWrapper by inject { parametersOf("session") }
    val oauth: RedisWrapper by inject { parametersOf("oauth_state",300) }

    fun generateId(): String {
        val bytes = ByteArray(32)                  // 256비트
        secureRandom.nextBytes(bytes)
        return Base64.getUrlEncoder()
            .withoutPadding()                       // '=' 패딩 제거
            .encodeToString(bytes)
    }


}