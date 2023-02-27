package com.example.myjobs.data.api

import com.example.myjobs.data.models.request.LoginRequest
import com.example.myjobs.data.models.request.ResetPasswordRequest
import com.example.myjobs.data.models.request.SignUpRequest
import com.example.myjobs.data.models.response.LoginResponse
import com.example.myjobs.data.models.response.NetworkResponse
import com.example.myjobs.data.models.response.ResetPasswordTokenResponse
import com.example.myjobs.data.models.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("/api/v1/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<NetworkResponse<LoginResponse>>

    @POST("/api/v1/auth/register")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<NetworkResponse<SignUpResponse>>

    @GET("/api/v1/auth/resetpassword")
    suspend fun getResetPasswordToken(@Query("email") email: String): Response<NetworkResponse<ResetPasswordTokenResponse>>

    @POST("/api/v1/auth/resetpassword")
    suspend fun resetPassword(@Body request:ResetPasswordRequest): Response<NetworkResponse<LoginResponse>>
}