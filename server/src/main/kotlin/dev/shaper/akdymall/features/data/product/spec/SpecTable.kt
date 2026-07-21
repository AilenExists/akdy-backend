package dev.shaper.akdymall.features.data.product.spec

import dev.shaper.akdymall.features.data.product.ProductTable
import dev.shaper.akdymall.features.common.database.BaseTable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.json.json

object SpecTable : LongIdTable("product_spec"), BaseTable {
    val productId = reference("product_id", ProductTable, ReferenceOption.CASCADE)
    val productData = json<JsonElement>("product_data", Json)
    val validationData = json<JsonElement>("validation_data", Json)
}