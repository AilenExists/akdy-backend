package dev.shaper.akdymall.features.data.user.identity

import dev.shaper.akdymall.features.data.user.UserTable
import dev.shaper.akdymall.features.common.database.BaseExpireTable
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable

object IdentityTable : LongIdTable("user_identity"), BaseExpireTable {
    val userId = reference("user_id", UserTable.id)
    val name = varchar("name", 255)
    val age = short("age")
    val gender = enumeration("gender", UserGender::class)
    val zipCode = varchar("zip_code", 255).nullable()
    val roadAddress = varchar("road_address", 255).nullable()
    val detailAddress = varchar("detail_address", 255).nullable()
}