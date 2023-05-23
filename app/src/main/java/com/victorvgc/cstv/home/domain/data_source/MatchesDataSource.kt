package com.victorvgc.cstv.home.domain.data_source

import com.victorvgc.cstv.home.domain.model.Match

interface MatchesDataSource {
    suspend fun getUpcomingMatchesList(page: Int): List<Match>
    suspend fun getRunningMatchesList(page: Int): List<Match>
}