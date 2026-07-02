package dev.shaper.akdymall.features.auth

import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.security.SecureRandom
import java.util.Base64
import java.util.UUID

@OptIn(ExperimentalLettuceCoroutinesApi::class)
object SessionService: KoinComponent {

    private val redisApi: RedisCoroutinesCommands<String, String> by inject()
    private val secureRandom = SecureRandom()

    fun generateId(): String {
        val bytes = ByteArray(32)                  // 256비트
        secureRandom.nextBytes(bytes)
        return Base64.getUrlEncoder()
            .withoutPadding()                       // '=' 패딩 제거
            .encodeToString(bytes)
    }

    suspend fun create(sessionId: String, userId: UUID, role: Int) {
        val json = Json.encodeToString(SessionData(userId, role))
        redisApi.setex("session:$sessionId", 1800, json)
    }

    suspend fun delete(sessionId: String) {
        redisApi.del("session:$sessionId")
    }

}