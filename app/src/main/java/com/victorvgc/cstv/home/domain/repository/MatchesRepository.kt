package com.victorvgc.cstv.home.domain.repository

import com.victorvgc.cstv.home.domain.model.Match

interface MatchesRepository {

    suspend fun getUpcomingMatches(page: Int): List<Match>

    suspend fun getRunningMatches(page: Int): List<Match>
}