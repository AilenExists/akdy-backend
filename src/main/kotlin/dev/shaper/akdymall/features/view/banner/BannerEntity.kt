package dev.shaper.akdymall.features.view.banner

import dev.shaper.akdymall.utils.ExposedUtils.copyFrom
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IntIdTable
import org.jetbrains.exposed.v1.dao.IntEntity
import org.jetbrains.exposed.v1.dao.IntEntityClass

class BannerEntity(id: EntityID<Int>): IntEntity(id) {

    object BannerTable : IntIdTable("banner") {
        val tag = varchar("tag", 255)
        val headline = varchar("headline", 255)
        val sub = varchar("sub", 255)
        val cta = varchar("cta", 255)
        val ctaSub = varchar("cta_sub", 255)
        val image = varchar("image", 255)
        val imageAlt = varchar("image_alt", 255)
        val badge = varchar("badge", 255)
    }
    companion object: IntEntityClass<BannerEntity>(BannerTable) 
    var tag by BannerTable.tag
    var headline by BannerTable.headline
    var sub by BannerTable.sub
    var cta by BannerTable.cta
    var ctaSub by BannerTable.ctaSub
    var image by BannerTable.image
    var imageAlt by BannerTable.imageAlt
    var badge by BannerTable.badge
    
    fun toBanner() = Banner(
        id.value,
        tag,
        headline,
        sub,
        cta,
        ctaSub,
        image,
        imageAlt,
        badge
    )
}