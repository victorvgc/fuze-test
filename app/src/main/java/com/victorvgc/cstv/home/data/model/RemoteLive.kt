package com.victorvgc.cstv.home.data.model

import com.victorvgc.cstv.home.domain.model.Live

data class RemoteLive(
    val opens_at: String?,
    val url: String?
) {
    fun toModel(): Live {
        return Live(
            url = url,
            opensAt = opens_at
        )
    }
}
