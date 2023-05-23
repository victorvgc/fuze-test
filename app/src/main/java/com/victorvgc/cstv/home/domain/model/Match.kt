package com.victorvgc.cstv.home.domain.model

import com.victorvgc.cstv.core.domain.model.Team

data class Match(
    val id: String,
    val league: League,
    val live: Live,
    val opponents: List<Team>,
    val serie: Serie,
    val beginAt: String
)