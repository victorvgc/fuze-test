package com.victorvgc.cstv.home.di

import com.victorvgc.cstv.home.data.data_source.MatchesDataSourceImpl
import com.victorvgc.cstv.home.data.repository.MatchesRepositoryImpl
import com.victorvgc.cstv.home.data.service.MatchesService
import com.victorvgc.cstv.home.domain.data_source.MatchesDataSource
import com.victorvgc.cstv.home.domain.repository.MatchesRepository
import com.victorvgc.cstv.home.domain.use_case.GetRunningMatches
import com.victorvgc.cstv.home.domain.use_case.GetUpcomingMatches
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ActHomeModule {
    @Provides
    fun provideGetUpcomingMatches(matchesRepository: MatchesRepository): GetUpcomingMatches =
        GetUpcomingMatches(matchesRepository)

    @Provides
    fun provideGetRunningMatches(matchesRepository: MatchesRepository): GetRunningMatches =
        GetRunningMatches(matchesRepository)

    @Provides
    fun provideMatchesService(retrofit: Retrofit): MatchesService =
        retrofit.create(MatchesService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun bindMatchesDataSource(matchesDataSourceImpl: MatchesDataSourceImpl): MatchesDataSource

    @Binds
    abstract fun bindMatchesRepository(matchesRepositoryImpl: MatchesRepositoryImpl): MatchesRepository
}