package dev.shaper.akdymall.features.structure.database

import dev.shaper.akdymall.features.data.product.ProductTable.defaultExpression
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.IColumnType
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.KotlinLocalDateTimeColumnType

interface BaseTable {
    fun <T> registerColumn(name:String, type: IColumnType<T & Any>) : Column<T>

    val createdAt: Column<LocalDateTime>
        get() = registerColumn("created_at", KotlinLocalDateTimeColumnType()).defaultExpression(CurrentDateTime)
    val updatedAt: Column<LocalDateTime>
        get() = registerColumn("updated_at", KotlinLocalDateTimeColumnType()).defaultExpression(CurrentDateTime)

}