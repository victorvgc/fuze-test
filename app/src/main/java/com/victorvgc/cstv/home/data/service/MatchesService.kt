package com.victorvgc.cstv.home.data.service

import com.victorvgc.cstv.core.utils.MAX_DATA_PER_PAGE
import com.victorvgc.cstv.home.data.model.RemoteMatch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MatchesService {

    @GET("matches/running?sort=begin_at&per_page=${MAX_DATA_PER_PAGE}")
    suspend fun getRunningMatches(@Query("page") page: Int): Response<List<RemoteMatch>>

    @GET("matches/upcoming?sort=begin_at&per_page=${MAX_DATA_PER_PAGE}")
    suspend fun getUpcomingMatches(@Query("page") page: Int): Response<List<RemoteMatch>>
}