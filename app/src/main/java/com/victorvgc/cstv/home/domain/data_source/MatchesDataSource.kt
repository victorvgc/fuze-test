package com.victorvgc.cstv.home.domain.data_source

interface MatchesDataSource {
    suspend fun getUpcomingMatchesList()
    suspend fun getRunningMatchesList()
}