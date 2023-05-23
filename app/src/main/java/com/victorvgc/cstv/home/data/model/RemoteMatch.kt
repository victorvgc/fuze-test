package com.victorvgc.cstv.home.data.model

import com.victorvgc.cstv.home.domain.model.Match

data class RemoteMatch(
    val begin_at: String,
    val id: String,
    val league: RemoteLeague,
    val live: RemoteLive,
    val serie: RemoteSerie,
    val opponents: List<RemoteOpponentsList>,
) {
    fun toModel(): Match {
        val teams = opponents.map { it.toModel() }

        return Match(
            id = id,
            league = league.toModel(),
            live = live.toModel(),
            serie = serie.toModel(),
            opponents = teams,
            beginAt = begin_at
        )
    }
}