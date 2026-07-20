package dev.shaper.akdymall.features.common.database

import kotlinx.datetime.LocalDateTime

interface BaseDTO {
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
}