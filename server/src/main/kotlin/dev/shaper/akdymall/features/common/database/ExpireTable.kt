package dev.shaper.akdymall.features.common.database

import dev.shaper.akdymall.features.common.database.TimestampTableUtils.cached
import dev.shaper.akdymall.features.data.product.ProductTable.clientDefault
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.datetime.KotlinLocalDateTimeColumnType
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

interface ExpireTable: TimestampTable {
    val expiresAt: Column<LocalDateTime>
        get() = cached("expires_at") {
            registerColumn("expires_at", KotlinLocalDateTimeColumnType()).clientDefault {
                Clock.System.now()
                    .plus(365.days)
                    .toLocalDateTime(TimeZone.currentSystemDefault())
            }
        }
}