package dev.shaper.akdymall.features.structure.database

import kotlinx.datetime.LocalDateTime

interface BaseExpireDTO: BaseDTO {
    val expiresAt: LocalDateTime
}