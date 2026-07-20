package dev.shaper.akdymall.features.data.product.option

import dev.shaper.akdymall.features.data.product.ProductTable
import dev.shaper.akdymall.features.common.database.BaseTable
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable

object OptionTable : LongIdTable("product_variant"), BaseTable {
    val productId = reference("product_id", ProductTable, ReferenceOption.CASCADE)
    val baseProduct = bool("base_product").default(true)
    val optionName = varchar("option_name", 255)
    val count = long("count")
    val priceFluctuation = long("price_fluctuation")
}