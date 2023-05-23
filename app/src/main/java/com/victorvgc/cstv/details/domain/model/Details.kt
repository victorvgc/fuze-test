package com.victorvgc.cstv.details.domain.model

import com.victorvgc.cstv.core.domain.model.Team

data class Details(
    val leagueSeriesName: String,
    val matchDate: String?,
    val opponents: List<Team>
)