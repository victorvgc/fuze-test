package com.victorvgc.cstv.home.data.model

import com.victorvgc.cstv.home.domain.model.League

data class RemoteLeague(
    val id: Int,
    val image_url: String?,
    val name: String,
) {
    fun toModel(): League {
        return League(
            id = id,
            imageUrl = image_url,
            name = name
        )
    }
}
