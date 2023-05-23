package com.victorvgc.cstv.core.data.model

import com.victorvgc.cstv.core.domain.model.Player

data class RemotePlayer(
    val first_name: String?,
    val last_name: String?,
    val image_url: String?,
    val name: String,
) {
    fun toModel(): Player {
        return Player(
            nickname = name,
            imageUrl = image_url,
            fullName = (first_name ?: "") + " " + (last_name ?: "")
        )
    }
}