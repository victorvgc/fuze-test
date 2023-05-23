package com.victorvgc.cstv.home.data.repository

import com.victorvgc.cstv.home.domain.data_source.MatchesDataSource
import com.victorvgc.cstv.home.domain.model.Match
import com.victorvgc.cstv.home.domain.repository.MatchesRepository
import javax.inject.Inject

class MatchesRepositoryImpl @Inject constructor(private val matchesSource: MatchesDataSource) :
    MatchesRepository {
    override suspend fun getUpcomingMatches(page: Int): List<Match> {
        return matchesSource.getUpcomingMatchesList(page)
    }

    override suspend fun getRunningMatches(page: Int): List<Match> {
        return matchesSource.getRunningMatchesList(page)
    }
}