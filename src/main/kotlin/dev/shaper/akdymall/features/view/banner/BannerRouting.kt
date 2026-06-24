package dev.shaper.akdymall.features.view.banner

import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import kotlin.getValue

fun Route.bannerRouting() {

    val bannerService: BannerService by inject()
    get("/api/banner") {

        val param = call.request.queryParameters["count"]?.toInt() ?: 3
        val res = bannerService.readBanners(param)

        call.respond(res)
    }
}