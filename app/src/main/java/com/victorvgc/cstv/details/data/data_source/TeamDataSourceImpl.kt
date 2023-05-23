package com.victorvgc.cstv.details.data.data_source

import com.victorvgc.cstv.core.domain.model.Team
import com.victorvgc.cstv.details.data.service.TeamService
import com.victorvgc.cstv.details.domain.data_source.TeamDataSource
import javax.inject.Inject

class TeamDataSourceImpl @Inject constructor(private val teamService: TeamService) :
    TeamDataSource {
    override suspend fun getTeamDetails(teamSlug: String): List<Team> {
        val response = teamService.getTeamBySlug(teamSlug = teamSlug)

        return if (response.isSuccessful) {
            response.body()?.map { it.toModel() } ?: emptyList()
        } else {
            emptyList()
        }
    }
}