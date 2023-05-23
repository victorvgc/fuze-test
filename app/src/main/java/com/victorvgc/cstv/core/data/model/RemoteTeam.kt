package com.victorvgc.cstv.core.data.model

import com.victorvgc.cstv.core.domain.model.Team

data class RemoteTeam(
    val acronym: String?,
    val image_url: String?,
    val name: String?,
    val players: List<RemotePlayer>,
    val slug: String?
) {
    fun toModel(): Team {
        val players = this.players.map { it.toModel() }

        return Team(
            name = name,
            acronym = acronym,
            imageUrl = image_url,
            players = players,
            slug = slug
        )
    }
}