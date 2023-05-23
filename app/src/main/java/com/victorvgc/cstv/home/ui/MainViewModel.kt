package com.victorvgc.cstv.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.victorvgc.cstv.home.domain.use_case.GetRunningMatches
import com.victorvgc.cstv.home.domain.use_case.GetUpcomingMatches
import com.victorvgc.cstv.home.ui.matches_list.MatchesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUpcomingMatches: GetUpcomingMatches,
    private val getRunningMatches: GetRunningMatches,
) : ViewModel() {

    val matchesFlow = Pager(PagingConfig(1)) {
        MatchesPagingSource(getUpcomingMatches, getRunningMatches)
    }.flow.cachedIn(viewModelScope)
}