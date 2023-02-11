package com.example.myjobs.data.repository

import com.example.myjobs.data.api.AuthApiService
import com.example.myjobs.data.models.request.LoginRequest
import com.example.myjobs.data.models.response.LoginResponse
import com.example.myjobs.data.models.response.NetworkResponse
import com.example.myjobs.utils.Resource
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService
) {
    suspend fun login(loginRequest: LoginRequest): Resource<NetworkResponse<LoginResponse>> {
        return try {
            val response = authApiService.login(loginRequest)
            val result = response.body()
            if(response.isSuccessful && result!=null){
                Resource.Success(result)
            }else{
                Resource.Error(response.message())
            }
        } catch (e:java.lang.Exception) {
            Resource.Error(e.message)
        }
    }

}