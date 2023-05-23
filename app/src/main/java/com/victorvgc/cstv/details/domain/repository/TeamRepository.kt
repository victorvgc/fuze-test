package com.victorvgc.cstv.details.domain.repository

import com.victorvgc.cstv.core.domain.model.Team

interface TeamRepository {
    suspend fun getTeamDetails(teamSlug: String): List<Team>?
}