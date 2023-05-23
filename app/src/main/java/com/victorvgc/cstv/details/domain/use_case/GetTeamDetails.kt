package com.victorvgc.cstv.details.domain.use_case

import com.victorvgc.cstv.core.domain.model.Team
import com.victorvgc.cstv.core.utils.Failure
import com.victorvgc.cstv.core.utils.UseCaseResponse
import com.victorvgc.cstv.details.domain.repository.TeamRepository
import javax.inject.Inject

class GetTeamDetails @Inject constructor(private val teamRepository: TeamRepository) {
    suspend operator fun invoke(teamSlug: String): UseCaseResponse<Team> {
        val response = teamRepository.getTeamDetails(teamSlug)
            ?: return UseCaseResponse.Error(failure = Failure(Failure.CODE_EMPTY_RESPONSE))

        var team: Team? = null

        for (t in response) {
            if (t.slug == teamSlug) {
                team = t
                break
            }
        }

        return if (team == null) {
            UseCaseResponse.Error(failure = Failure(Failure.CODE_EMPTY_RESPONSE))
        } else {
            UseCaseResponse.Success(team)
        }
    }
}