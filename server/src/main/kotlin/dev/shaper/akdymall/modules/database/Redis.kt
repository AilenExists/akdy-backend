package dev.shaper.akdymall.modules.database

import dev.shaper.akdymall.utils.ValueUtils.propertyGetter
import io.ktor.server.application.*
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.coroutines
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import org.koin.ktor.ext.getKoin


@OptIn(ExperimentalLettuceCoroutinesApi::class)
fun Application.configureRedis() {

    val builder = RedisURI.Builder
        .redis(
             propertyGetter("redis.url"),
            propertyGetter("redis.port").toInt()
        )
        .withDatabase(1)
        .build()
    // 추후 비밀번호 추가
    val redis: RedisClient = RedisClient.create(builder)
    val connection: StatefulRedisConnection<String, String> = redis.connect()
    val commands = connection.coroutines()
    getKoin().declare<RedisCoroutinesCommands<String, String>>(commands)

}