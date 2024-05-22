package com.erbe.feature.indihome.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @field:Json(name = "code")
    val code: Int?,
    @field:Json(name = "message")
    val message: String?
)