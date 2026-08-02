package dev.shaper.akdymall.features.data.manage.sync

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.features.common.database.TimestampDTO
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
@ExposedMapping(SyncTable::class)
data class Sync(
    @Id val id: Long,
    val productSync : LocalDateTime,
    val couponSync: LocalDateTime,
    val ratingSync: LocalDateTime,
    val reviewSync: LocalDateTime,
    val userSync: LocalDateTime,
    override val createdAt : LocalDateTime,
    override val updatedAt : LocalDateTime,
): TimestampDTO
