package com.erbe.feature.indihome.data.impl.mapper

import com.erbe.feature.indihome.data.api.model.Product
import com.erbe.feature.indihome.network.model.ProductResponse

fun ProductResponse.asExternalModel() =
    Product(
        productId!!,
        productName ?: "",
        productPrice ?: 0,
        image ?: "",
        brand ?: "",
        store ?: "",
        sale ?: 0,
        productRating ?: 0f
    )