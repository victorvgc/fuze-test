package com.victorvgc.cstv.core.data.interceptor

import com.victorvgc.cstv.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()

        val request: Request = original.newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.TOKEN}")
            .method(original.method, original.body)
            .build()

        return chain.proceed(request)
    }
}