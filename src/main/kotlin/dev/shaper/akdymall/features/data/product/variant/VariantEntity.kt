package dev.shaper.akdymall.features.data.product.variant

import dev.shaper.akdymall.features.data.product.ProductEntity
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.json.json

class VariantEntity(id : EntityID<Long>) : LongEntity(id) {
    
    object VariantTable : LongIdTable("product_variant") {
        val productId = reference("product_id", ProductEntity.ProductTable, ReferenceOption.CASCADE)
        val model = varchar("model", 255)
        val count = long("count")
        val color  = varchar("color", 255)
        val data = json<JsonElement>("data",Json)
        val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
        val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
    }
    
    companion object : LongEntityClass<VariantEntity>(VariantTable)
    var productId by VariantTable.productId
    var model by VariantTable.model
    var count by VariantTable.count
    var color by VariantTable.color
    var data by VariantTable.data
    var createdAt by VariantTable.createdAt
    var updatedAt by VariantTable.updatedAt
    
    fun toVariant() = Variant(
        id.value,
        productId.value,
        count,
        model,
        color,
        data,
        createdAt,
        updatedAt
    )
    
}