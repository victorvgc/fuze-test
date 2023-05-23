package com.victorvgc.cstv.details.domain.data_source

import com.victorvgc.cstv.core.domain.model.Team

interface TeamDataSource {
    suspend fun getTeamDetails(teamSlug: String): List<Team>?
}