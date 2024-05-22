package com.erbe.feature.indihome.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductPagingResponse(
    @field:Json(name = "itemsPerPage")
    val itemsPerPage: Int?,
    @field:Json(name = "currentItemCount")
    val currentItemCount: Int?,
    @field:Json(name = "pageIndex")
    val pageIndex: Int?,
    @field:Json(name = "totalPages")
    val totalPages: Int?,
    @field:Json(name = "items")
    val items: List<ProductResponse>?
)

@JsonClass(generateAdapter = true)
data class ProductResponse(
    @field:Json(name = "productId")
    val productId: String?,
    @field:Json(name = "productName")
    val productName: String?,
    @field:Json(name = "productPrice")
    val productPrice: Int?,
    @field:Json(name = "image")
    val image: String?,
    @field:Json(name = "brand")
    val brand: String?,
    @field:Json(name = "store")
    val store: String?,
    @field:Json(name = "sale")
    val sale: Int?,
    @field:Json(name = "productRating")
    val productRating: Float?
)

val dummyProductPagingResponse = ProductPagingResponse(
    itemsPerPage = 10,
    currentItemCount = 1,
    pageIndex = 1,
    totalPages = 1,
    items = listOf(
        ProductResponse(
            productId = "productId1",
            productName = "Lenovo Legion 7 16 I7 12700 32GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
            productPrice = 20000000,
            image = "",
            brand = "Lenovo",
            store = "LenovoStore",
            sale = 10,
            productRating = 4.5f
        )
    )
)
