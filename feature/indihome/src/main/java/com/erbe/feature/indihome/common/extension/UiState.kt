package com.erbe.feature.indihome.common.extension

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

sealed interface UiState<out T> {
    data object Init : UiState<Nothing>
    data object Loading : UiState<Nothing>
    data class Success<T>(val data: T) : UiState<T>
    data class Error(val error: Throwable? = null) : UiState<Nothing>
}

suspend fun <T> MutableStateFlow<UiState<T>>.asUiState(action: suspend () -> T) {
    this.update { UiState.Loading }
    try {
        val data = action()
        this.update { UiState.Success(data) }
    } catch (error: Throwable) {
        this.update { UiState.Error(error) }
    }
}

suspend fun <T> MutableSharedFlow<UiState<T>>.asUiState(action: suspend () -> T) {
    this.emit(UiState.Loading)
    try {
        val data = action()
        this.emit(UiState.Success(data))
    } catch (error: Throwable) {
        this.emit(UiState.Error(error))
    }
}

fun <T> Flow<T>.asUiState(): Flow<UiState<T>> {
    return this.map { data ->
        UiState.Success(data)
    }.catch { error ->
        UiState.Error(error)
    }
}

fun <T> convertFlow(action: suspend () -> T): Flow<UiState<T>> {
    return callbackFlow {
        send(UiState.Loading)
        try {
            val data = action()
            send(UiState.Success(data))
            close()
        } catch (error: Throwable) {
            send(UiState.Error(error))
            close()
        }
    }
}

inline fun <reified T> UiState<T>.data(): T? =
    if (this is UiState.Success) data else null

inline fun <reified T> UiState<T>.error(): Throwable? =
    if (this is UiState.Error) error else null

inline fun <reified T> UiState<T>.onInit(action: () -> Unit): UiState<T> = apply {
    if (this is UiState.Init) {
        Log.d("TAG", "onInit: ")
        action()
    }
}

inline fun <reified T> UiState<T>.onLoading(action: () -> Unit): UiState<T> = apply {
    if (this is UiState.Loading) {
        Log.d("TAG", "onLoading: ")
        action()
    }
}

inline fun <T> UiState<T>.onSuccess(action: (data: T) -> Unit): UiState<T> = apply {
    if (this is UiState.Success) {
        Log.d("TAG", "onSuccess: ")
        action(data)
    }
}

inline fun <T> UiState<T>.onError(action: (error: Throwable?) -> Unit): UiState<T> = apply {
    if (this is UiState.Error) {
        Log.d("TAG", "onError: ")
        action(error)
    }
}