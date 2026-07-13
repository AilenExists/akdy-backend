package dev.shaper.akdymall.features.data.product.variant

import dev.shaper.akdymall.features.data.product.ProductTable
import dev.shaper.akdymall.features.structure.database.BaseTable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.json.json

object VariantTable : LongIdTable("product_variant"), BaseTable {
    val productId = reference("product_id", ProductTable, ReferenceOption.CASCADE)
    val model = varchar("model", 255)
    val count = long("count")
    val color  = varchar("color", 255)
    val data = json<JsonElement>("data",Json)
}