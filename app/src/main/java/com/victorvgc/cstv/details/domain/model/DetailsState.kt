package com.victorvgc.cstv.details.domain.model

import com.victorvgc.cstv.core.utils.Failure

sealed class DetailsState {
    object Loading : DetailsState()
    data class Success(val data: Details) : DetailsState()
    data class Error(val msg: String? = null, val failure: List<Failure?> = emptyList()) :
        DetailsState()
}