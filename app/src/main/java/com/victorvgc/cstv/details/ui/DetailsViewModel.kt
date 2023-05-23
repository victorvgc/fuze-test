package com.victorvgc.cstv.details.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.victorvgc.cstv.core.utils.Failure
import com.victorvgc.cstv.details.domain.model.Details
import com.victorvgc.cstv.details.domain.model.DetailsState
import com.victorvgc.cstv.details.domain.use_case.GetTeamDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val getTeamDetails: GetTeamDetails) :
    ViewModel() {

    private val _detailsFlow = MutableStateFlow<DetailsState>(DetailsState.Loading)

    val detailsFlow: StateFlow<DetailsState> = _detailsFlow

    fun setData(leagueSerieName: String, team1Slug: String, team2Slug: String, matchDate: String?) {

        viewModelScope.launch {
            val team1 = getTeamDetails(team1Slug)
            val team2 = getTeamDetails(team2Slug)

            if (team1.isSuccess() && team2.isSuccess()) {
                _detailsFlow.tryEmit(
                    DetailsState.Success(
                        Details(
                            leagueSeriesName = leagueSerieName,
                            matchDate = matchDate,
                            opponents = listOf(
                                team1.getSuccess()!!.data,
                                team2.getSuccess()!!.data,
                            )
                        )
                    )
                )
            } else {
                val failures = mutableListOf<Failure>()

                if (!team1.isSuccess()) {
                    failures.add(team1.getError()!!.failure)
                }

                if (!team2.isSuccess()) {
                    failures.add(team2.getError()!!.failure)
                }

                _detailsFlow.tryEmit(
                    DetailsState.Error(
                        failure = failures
                    )
                )
            }
        }
    }
}