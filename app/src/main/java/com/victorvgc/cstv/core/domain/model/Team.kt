package com.victorvgc.cstv.core.domain.model

data class Team(
    val name: String?,
    val acronym: String?,
    val imageUrl: String?,
    val slug: String?,
    val players: List<Player>
)
