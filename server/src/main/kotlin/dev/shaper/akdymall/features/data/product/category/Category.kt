package dev.shaper.akdymall.features.data.product.category

import dev.shaper.akdymall.annotations.ExposedMapping
import dev.shaper.akdymall.annotations.Id
import dev.shaper.akdymall.annotations.ReadOnly
import dev.shaper.akdymall.annotations.Reference
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
@ExposedMapping(CategoryTable::class)
data class Category(
    @Id         val categoryId: Long,   //PK
    @Reference  val parentId: Long?,    //FK
    val name: String,
    val depth: Short,
    val sortOrder: Int,
    val isActive: Boolean,
    @ReadOnly val createdAt: LocalDateTime,
    @ReadOnly val updatedAt: LocalDateTime,
)
