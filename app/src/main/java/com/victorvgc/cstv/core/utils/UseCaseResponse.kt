package com.victorvgc.cstv.core.utils

sealed class UseCaseResponse<T> {
    data class Success<T>(val data: T) : UseCaseResponse<T>()
    data class Error<T>(val data: T? = null, val failure: Failure) : UseCaseResponse<T>()

    fun <K> fold(
        onSuccess: (success: Success<T>) -> K,
        onError: (error: Error<T>) -> K,
    ): K {
        return when (this) {
            is Success -> {
                onSuccess(this)
            }

            is Error -> {
                onError(this)
            }
        }
    }
}