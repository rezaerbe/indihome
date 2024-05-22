package com.erbe.feature.indihome.data.impl.mapper

import com.erbe.feature.indihome.data.api.model.ProductDetail
import com.erbe.feature.indihome.data.api.model.ProductVariant
import com.erbe.feature.indihome.network.model.ProductDetailResponse
import com.erbe.feature.indihome.network.model.ProductVariantResponse

fun ProductDetailResponse.asExternalModel() =
    ProductDetail(
        productId!!,
        productName ?: "",
        productPrice ?: 0,
        image ?: emptyList(),
        brand ?: "",
        description ?: "",
        store ?: "",
        sale ?: 0,
        stock ?: 0,
        totalRating ?: 0,
        totalReview ?: 0,
        totalSatisfaction ?: 0,
        productRating ?: 0f,
        productVariant?.map { it.asExternalModel() } ?: emptyList()
    )

fun ProductVariantResponse.asExternalModel() =
    ProductVariant(
        variantName ?: "",
        variantPrice ?: 0
    )