package com.victorvgc.cstv.home.data.model

data class RemoteMatch(
    val begin_at: String,
    val games: List<RemoteGame>,
    val id: String,
    val league: RemoteLeague,
    val live:RemoteLive
)