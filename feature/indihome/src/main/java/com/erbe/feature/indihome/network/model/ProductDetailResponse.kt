package com.erbe.feature.indihome.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDetailResponse(
    @field:Json(name = "productId")
    val productId: String?,
    @field:Json(name = "productName")
    val productName: String?,
    @field:Json(name = "productPrice")
    val productPrice: Int?,
    @field:Json(name = "image")
    val image: List<String>?,
    @field:Json(name = "brand")
    val brand: String?,
    @field:Json(name = "description")
    val description: String?,
    @field:Json(name = "store")
    val store: String?,
    @field:Json(name = "sale")
    val sale: Int?,
    @field:Json(name = "stock")
    val stock: Int?,
    @field:Json(name = "totalRating")
    val totalRating: Int?,
    @field:Json(name = "totalReview")
    val totalReview: Int?,
    @field:Json(name = "totalSatisfaction")
    val totalSatisfaction: Int?,
    @field:Json(name = "productRating")
    val productRating: Float?,
    @field:Json(name = "productVariant")
    val productVariant: List<ProductVariantResponse>?
)

@JsonClass(generateAdapter = true)
data class ProductVariantResponse(
    @field:Json(name = "variantName")
    val variantName: String?,
    @field:Json(name = "variantPrice")
    val variantPrice: Int?
)

val dummyProductDetailResponse =
    ProductDetailResponse(
        productId = "productId1",
        productName = "Lenovo Legion 7 16 I7 12700 32GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
        productPrice = 20000000,
        image = listOf(""),
        brand = "Lenovo",
        description = "This is description",
        store = "LenovoStore",
        sale = 10,
        stock = 10,
        totalRating = 2,
        totalReview = 2,
        totalSatisfaction = 100,
        productRating = 4.5f,
        listOf(
            ProductVariantResponse(
                variantName = "RAM 16GB",
                variantPrice = 0
            ),
            ProductVariantResponse(
                variantName = "RAM 32GB",
                variantPrice = 1000000
            )
        )
    )
