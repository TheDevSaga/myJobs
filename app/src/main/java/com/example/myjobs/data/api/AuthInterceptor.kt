package com.example.myjobs.data.api

import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sharedPreferences: SharedPreferences) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = sharedPreferences.getString("token",null)
        token?.let {
            println("TOKEN : $it")
            request.addHeader("Authorization", it) }
        return chain.proceed(request.build())
    }
}