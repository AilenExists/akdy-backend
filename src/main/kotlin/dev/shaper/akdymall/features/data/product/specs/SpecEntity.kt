package dev.shaper.akdymall.features.data.product.specs

import dev.shaper.akdymall.features.data.product.ProductEntity
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.dao.LongEntity
import org.jetbrains.exposed.v1.dao.LongEntityClass
import org.jetbrains.exposed.v1.datetime.CurrentDateTime
import org.jetbrains.exposed.v1.datetime.datetime
import org.jetbrains.exposed.v1.json.json

class SpecEntity(id : EntityID<Long>) : LongEntity(id) {

    object SpecTable : LongIdTable("product_spec") {
        val productId = reference("product_id", ProductEntity.ProductTable, ReferenceOption.CASCADE)
        val productData = json<JsonObject>("product_data", Json)
        val validationData = json<JsonObject>("validation_data", Json)
        val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
        val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
    }
    
    companion object : LongEntityClass<SpecEntity>(SpecTable)
    var productId by SpecTable.productId
    var productData by SpecTable.productData
    var validationData by SpecTable.validationData
    var createdAt by SpecTable.createdAt
    var updatedAt by SpecTable.updatedAt
    
    fun toSpec() = Spec(
        id.value,
        productId.value,
        productData,
        validationData,
        createdAt,
        updatedAt
    )

}