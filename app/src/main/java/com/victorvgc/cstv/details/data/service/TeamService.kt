package com.victorvgc.cstv.details.data.service

import com.victorvgc.cstv.core.data.model.RemoteTeam
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TeamService {

    @GET("teams?page=1&per_page=50")
    suspend fun getTeamBySlug(@Query("search[slug]") teamSlug: String): Response<List<RemoteTeam>>
}