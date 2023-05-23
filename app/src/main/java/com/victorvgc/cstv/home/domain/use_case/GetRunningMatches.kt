package com.victorvgc.cstv.home.domain.use_case

import com.victorvgc.cstv.core.utils.Failure
import com.victorvgc.cstv.core.utils.UseCaseResponse
import com.victorvgc.cstv.home.domain.model.Match
import com.victorvgc.cstv.home.domain.repository.MatchesRepository
import javax.inject.Inject

class GetRunningMatches @Inject constructor(private val matchesRepository: MatchesRepository) {
    suspend operator fun invoke(page: Int): UseCaseResponse<List<Match>> {
        val matches = matchesRepository.getRunningMatches(page)

        return if (matches.isEmpty()) {
            UseCaseResponse.Error(data = matches, Failure(code = Failure.CODE_EMPTY_RESPONSE))
        } else {
            UseCaseResponse.Success(data = matches)
        }
    }
}