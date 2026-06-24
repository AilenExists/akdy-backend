package dev.shaper.akdymall.features.view.banner

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.v1.core.Table

@Serializable
data class Banner(
    val id: Int,
    val tag: String,
    val headline: String,
    val sub: String,
    val cta: String,
    val ctaSub: String,
    val image: String,
    val imageAlt: String,
    val badge: String
)

