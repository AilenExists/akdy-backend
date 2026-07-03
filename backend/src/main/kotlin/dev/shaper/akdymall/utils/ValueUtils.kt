package dev.shaper.akdymall.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

object ValueUtils {
    fun nowTime() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}