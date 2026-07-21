package dev.shaper.akdymall.features.data.product

import dev.shaper.akdymall.features.common.route.DefaultErrorResponse
import dev.shaper.akdymall.features.common.route.ErrorCode
import dev.shaper.akdymall.features.common.route.RouteResponse.rejectKeyword
import dev.shaper.akdymall.features.data.product.category.CategoryResponse
import dev.shaper.akdymall.features.data.product.category.CategoryService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Route.productRouting() {

    val productService: ProductService by inject()
    val categoryService: CategoryService by inject()
    
    route("/products"){
        get("/search"){

            val searchOption = ProductSearchOption(
                keyword = call.parameters["keyword"] ?: return@get call.rejectKeyword(),
                count = call.parameters["count"]?.toIntOrNull() ?: 30,
                categoryId = call.parameters["categoryId"]?.toLongOrNull(),
                page = call.parameters["page"]?.toIntOrNull() ?: 1,
                sort = ProductSortOption.from(call.parameters["sort"]),
                priceRange = (call.parameters["minPrice"]?.toIntOrNull() ?: 0) to (call.parameters["maxPrice"]?.toIntOrNull() ?: 0)
            )

            // TODO
            // searchType에 따라 검색하는 방식 구현
            // count에 따라 갯수 제한 + count 갯수 제한 (강제로 많이 불러오기 방지)
            // sort, page 분기

        }
    }

    route("/categories"){
        get {
            val count = call.parameters["count"]?.toIntOrNull() ?: 10
            val categories = categoryService.readCategoryCount(count)
            val res = categories.map { category -> CategoryResponse(category.categoryId,category.name) }
            call.respond(
                HttpStatusCode.OK,
                res
            )
        }
    }

}