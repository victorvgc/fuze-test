package com.victorvgc.cstv.home.data.model

import com.victorvgc.cstv.core.domain.model.Team

data class RemoteOpponent(
    val acronym: String?,
    val image_url: String?,
    val name: String?,
    val slug: String?
) {
    fun toModel(): Team {
        return Team(
            name = name,
            acronym = acronym,
            imageUrl = image_url,
            slug = slug,
            players = emptyList()
        )
    }
}