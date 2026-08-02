package dev.shaper.akdymall.modules.database

import dev.shaper.akdymall.utils.ValueUtils.propertyGetter
import io.ktor.server.application.*
import io.lettuce.core.ClientOptions
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.MaintNotificationsConfig
import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.coroutines
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module
import org.koin.ktor.ext.getKoin


@OptIn(ExperimentalLettuceCoroutinesApi::class)
fun Application.configureRedis() {

    val builder = RedisURI.Builder
        .redis(
             propertyGetter("redis.host"),
            propertyGetter("redis.port").toInt()
        )
        .withDatabase(1)
        .build()
    // 추후 비밀번호 추가
    val redis: RedisClient = RedisClient.create(builder).apply {
        options = ClientOptions.builder()
            .maintNotificationsConfig(MaintNotificationsConfig.disabled())
            .build()
    }
    val connection: StatefulRedisConnection<String, String> = redis.connect()
    val commands = connection.coroutines()

    loadKoinModules(module {
        single<RedisCoroutinesCommands<String, String>> { commands }
    })

}