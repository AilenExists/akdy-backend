package dev.shaper.akdymall.utils

import io.ktor.server.application.Application
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

object ValueUtils {
    fun nowTime() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    fun Application.propertyGetter(path:String): String
            = environment.config.property(path).getString()
}