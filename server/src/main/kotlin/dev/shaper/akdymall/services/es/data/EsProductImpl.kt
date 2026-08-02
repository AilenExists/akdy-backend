package dev.shaper.akdymall.services.es.data

import co.elastic.clients.elasticsearch.ElasticsearchClient
import co.elastic.clients.elasticsearch._types.SortOrder
import dev.shaper.akdymall.features.data.product.ProductSearchOption
import dev.shaper.akdymall.features.data.product.ProductSortOption
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object EsProductImpl: KoinComponent {

    val esClient: ElasticsearchClient by inject()

    fun init() {
        val exists = esClient.indices()
            .exists { e -> e.index("akdymall-products") }
            .value()
        if (!exists) {
            esClient.indices().create { c ->
                c.index("akdymall-products")
                    .mappings { m ->
                        m.properties("id") { p -> p.long_ { l -> l } }
                            .properties("categoryId") { p -> p.long_ { l -> l } }
                            .properties("categoryName") { p -> p.keyword { k -> k } }  // 정확일치/필터용
                            .properties("name") { p ->
                                p.text { t ->
                                    t.analyzer("nori")
                                        .fields("raw") { f ->
                                            f.keyword { k ->
                                                k.ignoreAbove(256)  // 256자 초과 값은 색인 제외 (메모리 보호, 관례적 설정)
                                            }
                                        }
                                }
                            }
                            .properties("description") { p -> p.text { t -> t.analyzer("nori") } }
                            .properties("tags") { p -> p.keyword { k -> k } }          // 검색·필터용 (배열은 매핑 동일)
                            .properties("thumbnail") { p ->
                                p.keyword { k -> k.index(false) }                      // 표시용, 검색 불필요
                            }
                            .properties("badges") { p -> p.keyword { k -> k } }        // 필터/집계용
                            .properties("certQuote") { p ->
                                p.text { t -> t.analyzer("nori").index(false) }        // 표시용이면 index(false), 검색 대상이면 제거
                            }
                            .properties("sellerName") { p ->
                                p.text { t ->
                                    t.analyzer("nori")
                                        .fields("raw") { f -> f.keyword { k -> k.ignoreAbove(256) } }
                                }
                            }
                            .properties("location") { p -> p.keyword { k -> k } }      // 지역 필터용
                            .properties("price") { p -> p.long_ { l -> l } }
                            .properties("salePrice") { p -> p.long_ { l -> l } }       // 정렬·범위 필터용
                            .properties("discountRate") { p -> p.integer { i -> i } }
                            .properties("rating") { p -> p.double_ { d -> d } }        // 색인 시 ratingSum/ratingCount 계산값
                            .properties("quantitySum") { p -> p.long_ { l -> l } }
                            .properties("viewCount") { p -> p.long_ { l -> l } }
                            .properties("createdAt") { p -> p.date { d -> d } }
                    }
            }
        }
    }

    fun indexProduct(p: EsProductDoc) {
        esClient.index { i ->
            i.index("akdymall-products")
                .id(p.id.toString())      // 같은 id로 다시 넣으면 덮어쓰기 = upsert처럼 동작
                .document(p)
        }
    }

    // 삭제
    fun deleteProduct(id: Long) {
        esClient.delete { d -> d.index("akdymall-products").id(id.toString()) }
    }

    fun bulkIndex(products: List<EsProductDoc>) {
        esClient.bulk { b ->
            products.forEach { p ->
                b.operations { op ->
                    op.index { i ->
                        i.index("akdymall-products").id(p.id.toString()).document(p)
                    }
                }
            }
            b
        }
    }

    fun getProduct(id: Long): EsProductDoc? {
        val res = esClient.get({ g ->
            g.index("akdymall-products").id(id.toString())
        }, EsProductDoc::class.java)
        return if (res.found()) res.source() else null
    }

    fun syncViewCounts(updates: Map<Long, Long>) {   // productId → viewCount
        if (updates.isEmpty()) return
        esClient.bulk { b ->
            updates.forEach { (id, count) ->
                b.operations { op ->
                    op.update<EsProductDoc, Map<String,Any>> { u ->
                        u.index("akdymall-products")
                            .id(id.toString())
                            .action { a ->
                                a.doc(mapOf("viewCount" to count))   // 이 필드만 갱신
                            }
                    }
                }
            }
            b
        }
    }

    fun search(keyword: String, page: Int = 0, size: Int = 20): List<EsProductDoc> {
        val res = esClient.search({ s ->
            s.index("akdymall-products")
                .from(page * size)
                .size(size)
                .query { q ->
                    q.multiMatch { mm ->
                        mm.query(keyword)
                            .fields("name^3", "description")   // name 가중치 3배
                    }
                }
        }, EsProductDoc::class.java)
        return res.hits().hits().mapNotNull { it.source() }
    }

    fun searchWithFilter(
        option: ProductSearchOption
    ): List<EsProductDoc> {
        val res = esClient.search({ s ->
            s.index("akdymall-products")
                .from(option.page * option.count)
                .size(option.count)
                .query { q ->
                    q.bool { b ->
                        // must: 점수에 반영되는 검색어
                        b.must { m -> m.multiMatch { mm ->
                            mm.query(option.keyword).fields("name^3", "description")
                        }}
                        // filter: 점수 무관, 캐시되어 빠름 — 조건 필터는 여기에
                        if (option.categoryId != null) {
                            b.filter { f -> f.term { t ->
                                t.field("categoryId").value(option.categoryId)
                            }}
                        }
                        b.filter { f -> f.range { r ->
                            r.number { n ->
                                n.field("price")
                                option.priceRange.first.let { n.gte(it.toDouble()) }
                                option.priceRange.second.let { n.lte(it.toDouble()) }
                                n
                            }
                        }}
                        b.filter { f -> f.range { r ->
                            r.number { n -> n.field("quantitySum").gt(0.0) }
                        }}
                    }
                }
                .apply {
                    fun sortOption(value: String, order: SortOrder)
                        = sort { so -> so.field { f ->
                            f.field(value).order(order)
                        }}
                    when(option.sort) {
                        ProductSortOption.RECOMMENDED -> sort { so -> so.score { sc -> sc }}
                        ProductSortOption.CREATED_AT_ASC -> sortOption("createdAt", SortOrder.Asc)
                        ProductSortOption.CREATED_AT_DESC -> sortOption("createdAt", SortOrder.Desc)
                        ProductSortOption.NAME_ASC -> sortOption("name.raw", SortOrder.Asc)
                        ProductSortOption.NAME_DESC -> sortOption("name.raw", SortOrder.Desc)
                        ProductSortOption.PRICE_ASC -> sortOption("price", SortOrder.Asc)
                        ProductSortOption.PRICE_DESC -> sortOption("price", SortOrder.Desc)
                        ProductSortOption.VIEW_COUNT_ASC -> sortOption("viewCount", SortOrder.Asc)
                        ProductSortOption.VIEW_COUNT_DESC -> sortOption("viewCount", SortOrder.Desc)
                    }
                }
        }, EsProductDoc::class.java)
        return res.hits().hits().mapNotNull { it.source() }
    }

}