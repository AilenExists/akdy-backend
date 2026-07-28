package dev.shaper.akdymall.features.common.database

import kotlinx.datetime.LocalDateTime

interface BaseExpireDTO: BaseDTO {
    val expiresAt: LocalDateTime
}