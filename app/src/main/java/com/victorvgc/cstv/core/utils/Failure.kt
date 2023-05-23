package com.victorvgc.cstv.core.utils

class Failure(val code: Int, val error: Exception? = null) {
    companion object {
        const val CODE_EMPTY_RESPONSE = 0
    }
}