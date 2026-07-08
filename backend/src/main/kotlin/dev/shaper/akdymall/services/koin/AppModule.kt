package dev.shaper.akdymall.services.koin

import dev.shaper.akdymall.features.data.product.ProductService
import dev.shaper.akdymall.features.data.user.UserService
import dev.shaper.akdymall.features.view.banner.BannerService
import dev.shaper.akdymall.services.redis.RedisWrapper
import org.koin.dsl.module

object AppModule {

    val appModule = module {
        factory { params ->
            RedisWrapper(
                params.get(),
                params.getOrNull() ?: 1800L
            )
        }
    }

    val databaseModule = module {

        // UI/UX
        single { BannerService() }
        // Data
        single { UserService() }
        single { ProductService() }

    }

}