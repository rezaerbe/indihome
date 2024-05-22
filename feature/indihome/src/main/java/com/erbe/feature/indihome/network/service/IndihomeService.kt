package com.erbe.feature.indihome.network.service

import com.erbe.feature.indihome.network.model.BaseResponse
import com.erbe.feature.indihome.network.model.ProductDetailResponse
import com.erbe.feature.indihome.network.model.ProductPagingResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IndihomeService {

    @POST("products")
    suspend fun getProducts(
        @Query("search") search: String?,
        @Query("brand") brand: String?,
        @Query("lowest") lowest: Int?,
        @Query("highest") highest: Int?,
        @Query("sort") sort: String?,
        @Query("limit") limit: Int?,
        @Query("page") page: Int?
    ): BaseResponse<ProductPagingResponse>

    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: String): BaseResponse<ProductDetailResponse>

    @POST("search")
    suspend fun getSearch(@Query("query") query: String?): BaseResponse<List<String>>
}