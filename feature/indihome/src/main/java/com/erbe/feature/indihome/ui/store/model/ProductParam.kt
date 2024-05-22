package com.erbe.feature.indihome.ui.store.model

data class ProductParam(
    var search: String? = null,
    var brand: String? = null,
    var lowest: Int? = null,
    var highest: Int? = null,
    var sort: String? = null
)