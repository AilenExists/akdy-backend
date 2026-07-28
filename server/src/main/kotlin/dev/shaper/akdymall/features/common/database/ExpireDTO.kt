package dev.shaper.akdymall.features.common.database

import kotlinx.datetime.LocalDateTime

interface ExpireDTO: TimestampDTO {
    val expiresAt: LocalDateTime
}