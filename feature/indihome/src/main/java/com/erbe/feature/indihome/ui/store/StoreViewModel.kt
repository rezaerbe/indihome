package com.erbe.feature.indihome.ui.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erbe.feature.indihome.common.extension.UiState
import com.erbe.feature.indihome.common.extension.convertFlow
import com.erbe.feature.indihome.data.api.IndihomeRepository
import com.erbe.feature.indihome.ui.store.model.ProductParam
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class StoreViewModel(
    private val indihomeRepository: IndihomeRepository
) : ViewModel() {

    val isLinear = MutableStateFlow(true)

    fun setLinear() {
        isLinear.update { !it }
    }

    private val _param = MutableStateFlow(ProductParam())
    val param = _param.asStateFlow()

    fun setSearch(search: String?) {
        _param.update { it.copy(search = search) }
    }

    fun setQuery(brand: String?, lowest: Int?, highest: Int?, sort: String?) {
        _param.update { it.copy(brand = brand, lowest = lowest, highest = highest, sort = sort) }
    }

    fun resetParam() {
        _param.update {
            it.copy(search = null, brand = null, lowest = null, highest = null, sort = null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val products = param.flatMapLatest { query ->
        convertFlow {
            indihomeRepository.getProducts(
                query.search,
                query.brand,
                query.lowest,
                query.highest,
                query.sort
            )
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState.Init)
}