package com.example.myjobs.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.example.myjobs.data.api.AuthApiService
import com.example.myjobs.data.db.UserDao
import com.example.myjobs.data.models.local.User
import com.example.myjobs.data.models.request.LoginRequest
import com.example.myjobs.data.models.request.ResetPasswordRequest
import com.example.myjobs.data.models.request.SignUpRequest
import com.example.myjobs.data.models.response.LoginResponse
import com.example.myjobs.data.models.response.NetworkResponse
import com.example.myjobs.data.models.response.ResetPasswordTokenResponse
import com.example.myjobs.data.models.response.SignUpResponse
import com.example.myjobs.utils.Resource
import kotlinx.coroutines.Dispatchers
import safeApiCall
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
    private  val userDao:UserDao,
    private val sharedPreferences: SharedPreferences
) {
    suspend fun login(loginRequest: LoginRequest): Resource<NetworkResponse<LoginResponse>> {
        return try {
            safeApiCall(Dispatchers.IO){authApiService.login(loginRequest)}
        } catch (e:java.lang.Exception) {
            Resource.Error(e.message)
        }
    }
    suspend fun signUp(signUpRequest: SignUpRequest): Resource<NetworkResponse<SignUpResponse>> {
        return try {
            safeApiCall(Dispatchers.IO){authApiService.signUp(signUpRequest)}
        } catch (e:java.lang.Exception) {
            Resource.Error(e.message)
        }
    }
    suspend fun getResetPasswordToken(email: String): Resource<NetworkResponse<ResetPasswordTokenResponse>> {
        return try {
            safeApiCall(Dispatchers.IO){authApiService.getResetPasswordToken(email)}
        } catch (e:java.lang.Exception) {
            Resource.Error(e.message)
        }
    }
    suspend fun resetPassword(request: ResetPasswordRequest): Resource<NetworkResponse<LoginResponse>> {
        return try {
            safeApiCall(Dispatchers.IO){authApiService.resetPassword(request)}
        } catch (e:java.lang.Exception) {
            Resource.Error(e.message)
        }
    }
     fun getUser(): LiveData<User> {
        return userDao.getUser()
    }
    suspend fun setUser(user: User):Long{
        val editor = sharedPreferences.edit()
        editor.putString("token", user.token)
        editor.apply()
        return userDao.setUser(user)
    }
}