package com.victorvgc.cstv.home.data.model

data class RemoteGame(
    val begin_at: String?,
    val complete: Boolean,
    val end_at: String?,
    val finished: Boolean,
    val id: String,
    val match_id: String,
    val position: Int,
    val status: String,
)
