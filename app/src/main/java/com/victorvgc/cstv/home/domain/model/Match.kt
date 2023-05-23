package com.victorvgc.cstv.home.domain.model

data class Match(
    val id: String,
    val league: League,
    val live: Live,
    val opponents: List<Team>,
    val serie: Serie,
    val beginAt: String
)