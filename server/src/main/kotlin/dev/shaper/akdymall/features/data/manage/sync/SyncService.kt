package dev.shaper.akdymall.features.data.manage.sync

import dev.shaper.akdymall.utils.ValueUtils.getCurrent
import dev.shaper.akdymall.utils.ValueUtils.minusSeconds
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import org.jetbrains.exposed.v1.core.max
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update

class SyncService {

    fun init() = transaction {
        val current = getCurrent()
        SyncTable.insert {
            it[productSync] = current
            it[userSync] = current
            it[couponSync] = current
            it[ratingSync] = current
            it[reviewSync] = current
            it[updatedAt] = current
        }
    }

    fun getLastProductSync(): LocalDateTime? = transaction {
        val maxExpr = SyncTable.productSync.max()
        SyncTable.select(maxExpr)
            .single()[maxExpr]
            ?.minusSeconds(5)
    }

    fun getLastRatingSync(): LocalDateTime? = transaction {
        val maxExpr = SyncTable.ratingSync.max()
        SyncTable.select(maxExpr)
            .single()[maxExpr]
            ?.minusSeconds(5)
    }

    fun syncProduct() = transaction {
        SyncTable.update {
            it[productSync] = getCurrent()
            it[updatedAt] = getCurrent()
        }
    }

    fun syncRating() = transaction {
        SyncTable.insert {
            it[ratingSync] = getCurrent()
            it[updatedAt] = getCurrent()
        }
    }

    fun syncUser() = transaction {
        SyncTable.insert {
            it[userSync] = getCurrent()
            it[updatedAt] = getCurrent()
        }
    }

    fun syncCoupon() = transaction {
        SyncTable.insert {
            it[couponSync] = getCurrent()
            it[updatedAt] = getCurrent()
        }
    }

    fun syncReview() = transaction {
        SyncTable.insert {
            it[reviewSync] = getCurrent()
            it[updatedAt] = getCurrent()
        }
    }

}