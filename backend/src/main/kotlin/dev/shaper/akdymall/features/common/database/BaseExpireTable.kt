package dev.shaper.akdymall.features.structure.database

import dev.shaper.akdymall.features.data.product.ProductTable.clientDefault
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.datetime.KotlinLocalDateTimeColumnType
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

interface BaseExpireTable: BaseTable {
    val expireAt: Column<LocalDateTime>
        get() = registerColumn("expires_at", KotlinLocalDateTimeColumnType()).clientDefault {
            Clock.System.now()
                .plus(365.days)
                .toLocalDateTime(TimeZone.currentSystemDefault())
        }
}