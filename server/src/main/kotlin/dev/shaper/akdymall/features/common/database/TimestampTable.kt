package dev.shaper.akdymall.features.common.database

import dev.shaper.akdymall.features.common.database.TimestampTableUtils.cached
import dev.shaper.akdymall.features.data.product.ProductTable.defaultExpression
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.IColumnType
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.KotlinLocalDateTimeColumnType
import java.util.concurrent.ConcurrentHashMap

interface BaseTable {
    fun <T> registerColumn(name:String, type: IColumnType<T & Any>) : Column<T>

    val createdAt: Column<LocalDateTime>
        get() = cached("created_at") {
            registerColumn<LocalDateTime>("created_at", KotlinLocalDateTimeColumnType())
                .defaultExpression(CurrentDateTime)
        }

    val updatedAt: Column<LocalDateTime>
        get() = cached("updated_at") {
            registerColumn<LocalDateTime>("updated_at", KotlinLocalDateTimeColumnType())
                .defaultExpression(CurrentDateTime)
        }
}