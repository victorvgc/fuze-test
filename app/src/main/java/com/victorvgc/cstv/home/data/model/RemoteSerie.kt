package com.victorvgc.cstv.home.data.model

import com.victorvgc.cstv.home.domain.model.Serie

data class RemoteSerie(
    val id: Int,
    val name: String?
) {
    fun toModel(): Serie {
        return Serie(
            id = id,
            name = name ?: ""
        )
    }
}