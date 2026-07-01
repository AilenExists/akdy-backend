package dev.shaper.akdymall.di

import dev.shaper.akdymall.features.data.product.ProductService
import dev.shaper.akdymall.features.data.user.UserService
import dev.shaper.akdymall.features.view.banner.BannerService
import org.koin.dsl.module

object AppModule {
    val appModule = module {

    }

    val databaseModule = module {

        single { BannerService() }

        single { UserService() }
        single { ProductService() }

    }

}
