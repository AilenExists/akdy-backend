package dev.shaper.akdymall.features.data.review

import dev.shaper.akdymall.features.data.product.ProductTable
import dev.shaper.akdymall.features.data.user.UserTable
import dev.shaper.akdymall.features.common.database.BaseTable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.json.json

object ReviewTable: LongIdTable("review"), BaseTable {
    val productId = reference("product_id", ProductTable)
    val userId = reference("user_id", UserTable)
    val rating = short("rating")
    val title = varchar("title", 255)
    val content = varchar("content", 255)
    val images = json<JsonElement>("images",Json)
    val likes = integer("likes")
    val isBest = bool("is_best")
    val isDeleted = bool("is_deleted")
}