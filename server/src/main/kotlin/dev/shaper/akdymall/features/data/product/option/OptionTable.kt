package dev.shaper.akdymall.features.data.product.option

import dev.shaper.akdymall.features.data.product.ProductTable
import dev.shaper.akdymall.features.common.database.TimestampTable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.json.json

object OptionTable : LongIdTable("product_option"), TimestampTable {
    val productId = reference("product_id", ProductTable, ReferenceOption.CASCADE)
    val isBasic = bool("is_basic").default(true)
    val isEnabled = bool("enabled").default(true)
    val optionName = varchar("option_name", 255)
    val options = json<JsonElement>("option",Json)
    val quantity = long("quantity")
    val priceFluctuation = long("price_fluctuation")
}