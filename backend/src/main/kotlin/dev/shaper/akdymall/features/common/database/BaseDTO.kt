package dev.shaper.akdymall.features.structure.database

import kotlinx.datetime.LocalDateTime

interface BaseDTO {
    val createdAt: LocalDateTime
    val updatedAt: LocalDateTime
}