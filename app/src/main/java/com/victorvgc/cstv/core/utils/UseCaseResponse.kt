package com.victorvgc.cstv.core.utils

sealed class UseCaseResponse<T> {
    data class Success<T>(val data: T) : UseCaseResponse<T>() {
        override fun isSuccess(): Boolean = true

        override fun getError(): Error<T>? = null
        override fun getSuccess(): Success<T> = this
    }

    data class Error<T>(val data: T? = null, val failure: Failure) : UseCaseResponse<T>() {
        override fun isSuccess(): Boolean = false

        override fun getError(): Error<T> = this

        override fun getSuccess(): Success<T>? = null
    }

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

    abstract fun isSuccess(): Boolean

    abstract fun getError(): Error<T>?

    abstract fun getSuccess(): Success<T>?
}