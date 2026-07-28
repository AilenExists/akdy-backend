package dev.shaper.akdymall.utils

import io.ktor.server.application.Application
import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

object ValueUtils {
    fun getCurrent() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    fun LocalDateTime.minusSeconds(seconds: Int):LocalDateTime {
        val timezone = TimeZone.currentSystemDefault()
        val instant = this.toInstant(timezone)
        val updatedInstant = instant.minus(DateTimePeriod(seconds = seconds), timezone)
        return updatedInstant.toLocalDateTime(timezone)
    }
    fun Application.propertyGetter(path:String): String
            = environment.config.property(path).getString()
}