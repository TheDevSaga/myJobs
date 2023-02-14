package com.example.myjobs.data.api

import com.example.myjobs.data.models.request.LoginRequest
import com.example.myjobs.data.models.request.SignUpRequest
import com.example.myjobs.data.models.response.LoginResponse
import com.example.myjobs.data.models.response.NetworkResponse
import com.example.myjobs.data.models.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

@POST("/api/v1/auth/login")
suspend fun login(@Body loginRequest: LoginRequest):Response<NetworkResponse<LoginResponse>>
@POST("/api/v1/auth/register")
suspend fun signUp(@Body signUpRequest: SignUpRequest):Response<NetworkResponse<SignUpResponse>>

}