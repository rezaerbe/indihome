package com.erbe.feature.indihome.data.impl

import com.erbe.feature.indihome.common.helper.mapSafe
import com.erbe.feature.indihome.data.api.IndihomeRepository
import com.erbe.feature.indihome.data.api.model.Product
import com.erbe.feature.indihome.data.api.model.ProductDetail
import com.erbe.feature.indihome.data.impl.mapper.asExternalModel
import com.erbe.feature.indihome.network.service.IndihomeService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class IndihomeRepositoryImpl(
    private val indihomeService: IndihomeService,
    private val ioDispatcher: CoroutineDispatcher
) : IndihomeRepository {

    override suspend fun getProducts(
        search: String?,
        brand: String?,
        lowest: Int?,
        highest: Int?,
        sort: String?
    ): List<Product> =
        withContext(ioDispatcher) {
            indihomeService.getProducts(
                search = search,
                brand = brand,
                lowest = lowest,
                highest = highest,
                sort = sort,
                limit = 32,
                page = 1
            ).data?.items?.mapSafe { productsResponse ->
                productsResponse.asExternalModel()
            } ?: throw Exception("Empty")
        }

    override suspend fun getProductDetail(id: String?): ProductDetail =
        withContext(ioDispatcher) {
            id ?: throw Exception("Empty")
            indihomeService.getProductDetail(id).data?.asExternalModel() ?: throw Exception("Empty")
        }

    override suspend fun getSearch(query: String?): List<String> =
        withContext(ioDispatcher) {
            indihomeService.getSearch(query).data ?: throw Exception("Empty")
        }
}