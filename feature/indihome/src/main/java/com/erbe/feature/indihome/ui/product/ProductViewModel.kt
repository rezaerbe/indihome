package com.erbe.feature.indihome.ui.product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erbe.feature.indihome.common.extension.UiState
import com.erbe.feature.indihome.common.extension.convertFlow
import com.erbe.feature.indihome.data.api.IndihomeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class ProductViewModel(
    private val indihomeRepository: IndihomeRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val detailProduct = savedStateHandle.getStateFlow("productId", "").flatMapLatest { id ->
        convertFlow {
            indihomeRepository.getProductDetail(id)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, UiState.Init)

    fun setProductId(id: String) {
        savedStateHandle["productId"] = id
    }
}