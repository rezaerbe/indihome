package com.erbe.feature.indihome.data.api

import com.erbe.feature.indihome.data.api.model.Product
import com.erbe.feature.indihome.data.api.model.ProductDetail

interface IndihomeRepository {

    suspend fun getProducts(
        search: String?,
        brand: String?,
        lowest: Int?,
        highest: Int?,
        sort: String?
    ): List<Product>

    suspend fun getProductDetail(id: String?): ProductDetail

    suspend fun getSearch(query: String?): List<String>
}