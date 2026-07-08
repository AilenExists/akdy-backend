package dev.shaper.akdymall.services.redis

import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalLettuceCoroutinesApi::class)
open class RedisWrapper(
    val keyName: String,
    val expire:Long = 1800
): KoinComponent {

    @PublishedApi
   internal val redisApi: RedisCoroutinesCommands<String, String> by inject()

    suspend inline fun <reified T> create(id: String, value:T) : Boolean {
        val json = Json.encodeToString(value)
        val res = redisApi.setex("$keyName:$id", expire, json)
        return res != null
    }

    suspend inline fun <reified T> get(id:String) : T? {
        val res = redisApi.get("$keyName:$id")
        return if(res != null) Json.Default.decodeFromString(res) else null
    }

    suspend fun delete(id: String) : Boolean {
        return redisApi.del("$keyName:$id") != null
    }

}