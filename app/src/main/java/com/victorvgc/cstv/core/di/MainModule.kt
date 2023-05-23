package com.victorvgc.cstv.core.di

import com.victorvgc.cstv.BuildConfig
import com.victorvgc.cstv.core.data.interceptor.AuthInterceptor
import com.victorvgc.cstv.core.utils.TIMEOUT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object MainModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().also {
            it.level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
        client.readTimeout(TIMEOUT, TimeUnit.SECONDS)
        client.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        client.addInterceptor(loggingInterceptor)
        client.addInterceptor(AuthInterceptor())


        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())

        return retrofit.build()
    }
}