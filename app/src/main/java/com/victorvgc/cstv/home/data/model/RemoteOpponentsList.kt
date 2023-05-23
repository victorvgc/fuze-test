package com.victorvgc.cstv.home.data.model

import com.victorvgc.cstv.home.domain.model.Team

data class RemoteOpponentsList(
    val opponent: RemoteOpponent,
    val type: String
) {
    fun toModel(): Team {
        return opponent.toModel()
    }
}