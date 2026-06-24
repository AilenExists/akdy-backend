package dev.shaper.akdymall.features.view.banner

import dev.shaper.akdymall.utils.ExposedUtils.copyFrom
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class BannerService {

    fun createBanner(banner: Banner): Banner = transaction {
        BannerEntity.new {
            copyFrom(banner)
        }.toBanner()
    }

    fun readBannerById(bannerId: Int): Banner? = transaction {
        BannerEntity.findById(bannerId)?.toBanner()
    }

    fun readBanners(count:Int = 3): List<Banner> = transaction {
        BannerEntity.all().limit(3).map { it.toBanner() }
    }

    fun updateBanner(banner: Banner): Boolean = transaction {
        val entity = BannerEntity.findById(banner.id) ?: return@transaction false
        entity.copyFrom(banner)
        true
    }

    fun deleteBanner(bannerId: Int): Boolean = transaction {
       BannerEntity.findById(bannerId)?.delete()
           ?: return@transaction false
        true
    }

}