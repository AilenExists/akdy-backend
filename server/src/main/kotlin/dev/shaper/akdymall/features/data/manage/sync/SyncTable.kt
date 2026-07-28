package dev.shaper.akdymall.features.data.manage.sync

import dev.shaper.akdymall.features.common.database.TimestampTable
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.datetime.datetime

object SyncTable: LongIdTable("sync"), TimestampTable {
    val productSync = datetime("product_sync")
    val ratingSync = datetime("rating_sync")
    val userSync = datetime("user_sync")
    val reviewSync = datetime("review_sync")
    val couponSync = datetime("coupon_sync")
}