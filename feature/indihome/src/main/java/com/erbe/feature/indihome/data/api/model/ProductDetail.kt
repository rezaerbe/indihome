package com.erbe.feature.indihome.data.api.model

data class ProductDetail(
    val productId: String,
    val productName: String,
    val productPrice: Int,
    val image: List<String>,
    val brand: String,
    val description: String,
    val store: String,
    val sale: Int,
    val stock: Int,
    val totalRating: Int,
    val totalReview: Int,
    val totalSatisfaction: Int,
    val productRating: Float,
    var productVariant: List<ProductVariant>
)

data class ProductVariant(
    val variantName: String,
    val variantPrice: Int
)

val dummyProductDetail =
    ProductDetail(
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
            ProductVariant(
                variantName = "RAM 16GB",
                variantPrice = 0
            ),
            ProductVariant(
                variantName = "RAM 32GB",
                variantPrice = 1000000
            )
        )
    )