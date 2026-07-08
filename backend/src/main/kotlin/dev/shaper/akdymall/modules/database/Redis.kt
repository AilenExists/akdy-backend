package dev.shaper.akdymall.modules.database

import io.ktor.server.application.*
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.coroutines
import io.lettuce.core.api.coroutines.RedisCoroutinesCommands
import org.koin.ktor.ext.getKoin


@OptIn(ExperimentalLettuceCoroutinesApi::class)
fun Application.configureRedis() {

    val redis: RedisClient = RedisClient.create("redis://redis:6379/0")
    val connection: StatefulRedisConnection<String, String> = redis.connect()
    val commands = connection.coroutines()
    getKoin().declare<RedisCoroutinesCommands<String, String>>(commands)

}