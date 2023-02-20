package com.example.myjobs.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        token?.let {
            request.addHeader("Authorization", it) }
        return chain.proceed(request.build())
    }
}