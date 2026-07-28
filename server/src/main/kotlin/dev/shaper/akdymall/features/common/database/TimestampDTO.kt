package dev.shaper.akdymall.features.common.database

import kotlinx.datetime.LocalDateTime

interface TimestampDTO {
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
}