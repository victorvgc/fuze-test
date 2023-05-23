package com.victorvgc.cstv.details.di

import com.victorvgc.cstv.details.data.data_source.TeamDataSourceImpl
import com.victorvgc.cstv.details.data.repository.TeamRepositoryImpl
import com.victorvgc.cstv.details.data.service.TeamService
import com.victorvgc.cstv.details.domain.data_source.TeamDataSource
import com.victorvgc.cstv.details.domain.repository.TeamRepository
import com.victorvgc.cstv.details.domain.use_case.GetTeamDetails
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailsModule {

    @Binds
    abstract fun bindTeamRepository(teamRepositoryImpl: TeamRepositoryImpl): TeamRepository

    @Binds
    abstract fun bindTeamDataSource(teamDataSourceImpl: TeamDataSourceImpl): TeamDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object ProvDetailsModule {

    @Provides
    fun provideGetTeamDetails(teamRepository: TeamRepository): GetTeamDetails =
        GetTeamDetails(teamRepository)

    @Provides
    fun provideTeamService(retrofit: Retrofit): TeamService =
        retrofit.create(TeamService::class.java)
}