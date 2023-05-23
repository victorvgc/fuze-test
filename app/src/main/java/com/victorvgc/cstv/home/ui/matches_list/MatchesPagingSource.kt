package com.victorvgc.cstv.home.ui.matches_list

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.victorvgc.cstv.home.domain.model.Match
import com.victorvgc.cstv.home.domain.use_case.GetRunningMatches
import com.victorvgc.cstv.home.domain.use_case.GetUpcomingMatches

class MatchesPagingSource(
    private val getUpcomingMatches: GetUpcomingMatches,
    private val getRunningMatches: GetRunningMatches
) : PagingSource<Int, Match>() {
    override fun getRefreshKey(state: PagingState<Int, Match>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Match> {
        return try {
            val nextPageNumber = params.key ?: 1
            val upcoming = getUpcomingMatches(nextPageNumber)
            val running = getRunningMatches(nextPageNumber)

            val runningList = running.fold(
                { success -> success.data },
                { error -> error.data ?: emptyList() }
            )

            val upcomingList = upcoming.fold(
                { success ->
                    success.data
                },
                { error ->
                    error.data ?: emptyList()
                }
            )

            val allMatchesList = mutableListOf<Match>()
            allMatchesList.addAll(runningList)
            allMatchesList.addAll(upcomingList)

            LoadResult.Page(
                data = allMatchesList,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (allMatchesList.isNotEmpty()) nextPageNumber + 1 else null
            )

        } catch (e: Exception) {
            Log.e("LOAD MATCHES", e.stackTraceToString())
            LoadResult.Error(e)
        }
    }
}