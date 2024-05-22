package com.erbe.feature.indihome.ui.store.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erbe.feature.indihome.common.extension.UiState
import com.erbe.feature.indihome.common.extension.asUiState
import com.erbe.feature.indihome.data.api.IndihomeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val DELAY_DEBOUNCE = 1000L

class SearchViewModel(
    private val indihomeRepository: IndihomeRepository
) : ViewModel() {

    private val _search = MutableStateFlow<UiState<List<String>>>(UiState.Init)
    val search = _search.asStateFlow()

    private var job: Job? = null

    fun getSearch(query: String?) {
        job?.cancel()
        job = viewModelScope.launch {
            delay(DELAY_DEBOUNCE)
            _search.asUiState {
                indihomeRepository.getSearch(query)
            }
        }
    }

    fun reset() {
        viewModelScope.launch {
            _search.update { UiState.Init }
        }
    }
}