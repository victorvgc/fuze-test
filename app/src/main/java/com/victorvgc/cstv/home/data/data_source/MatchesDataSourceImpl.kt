package com.victorvgc.cstv.home.data.data_source

import com.victorvgc.cstv.home.data.service.MatchesService
import com.victorvgc.cstv.home.domain.data_source.MatchesDataSource
import com.victorvgc.cstv.home.domain.model.Match
import javax.inject.Inject

class MatchesDataSourceImpl @Inject constructor(private val matchesService: MatchesService) :
    MatchesDataSource {
    override suspend fun getUpcomingMatchesList(page: Int): List<Match> {
        val result = matchesService.getUpcomingMatches(page)

        return if (result.isSuccessful) {
            val list = result.body() ?: emptyList()

            list.map { it.toModel() }
        } else {
            emptyList()
        }
    }

    override suspend fun getRunningMatchesList(page: Int): List<Match> {
        val result = matchesService.getRunningMatches(page)

        return if (result.isSuccessful) {
            val list = result.body() ?: emptyList()

            list.map { it.toModel() }
        } else {
            emptyList()
        }
    }
}