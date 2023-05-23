package com.victorvgc.cstv.details.data.repository

import com.victorvgc.cstv.core.domain.model.Team
import com.victorvgc.cstv.details.domain.data_source.TeamDataSource
import com.victorvgc.cstv.details.domain.repository.TeamRepository
import javax.inject.Inject

class TeamRepositoryImpl @Inject constructor(private val teamDataSource: TeamDataSource) :
    TeamRepository {
    override suspend fun getTeamDetails(teamSlug: String): List<Team>? {
        return teamDataSource.getTeamDetails(teamSlug)
    }
}