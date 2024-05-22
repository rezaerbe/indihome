package com.erbe.feature.indihome.common.helper

fun <T, U> List<T>.mapSafe(
    mapAction: (T) -> U
): List<U> {
    return this.mapNotNull { data ->
        try {
            mapAction(data)
        } catch (error: Throwable) {
            null
        }
    }.ifEmpty {
        throw Exception("Empty")
    }
}