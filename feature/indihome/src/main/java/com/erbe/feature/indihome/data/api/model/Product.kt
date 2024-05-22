package com.erbe.feature.indihome.data.api.model

data class Product(
    val productId: String,
    val productName: String,
    val productPrice: Int,
    val image: String,
    val brand: String,
    val store: String,
    val sale: Int,
    val productRating: Float
)

val dummyProduct =
    Product(
        productId = "productId1",
        productName = "Lenovo Legion 7 16 I7 12700 32GB 1TB SSD RTX3070 8GB Windows 11 QHD IPS",
        productPrice = 20000000,
        image = "",
        brand = "Lenovo",
        store = "LenovoStore",
        sale = 10,
        productRating = 4.5f
    )

val dummyProduct2 =
    Product(
        productId = "productId2",
        productName = "Lenovo Legion 5 16 I5 12500 16GB 512GB SSD RTX3060 6GB Windows 11 FHD IPS",
        productPrice = 10000000,
        image = "",
        brand = "Lenovo",
        store = "LenovoStore",
        sale = 5,
        productRating = 5f
    )