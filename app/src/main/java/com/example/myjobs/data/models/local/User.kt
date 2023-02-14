package com.example.myjobs.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myjobs.data.models.response.LoginResponse
import com.example.myjobs.data.models.response.SignUpResponse

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    var uid: Int?,
    val createdAt: String,
    val email: String,
    val id: String,
    val name: String,
    val skills: String,
    val token: String,
    val updatedAt: String,
    val userRole: Int
){
    companion object{
        fun from(loginResponse: LoginResponse):User{
            return User(
                null,
                loginResponse.createdAt,
                loginResponse.email,
                loginResponse.id,
                loginResponse.name,
                loginResponse.skills,
                loginResponse.token,
                loginResponse.updatedAt,
                loginResponse.userRole
            )
        }
        fun from(signUpResponse: SignUpResponse):User{
            return User(
                null,
                signUpResponse.createdAt,
                signUpResponse.email,
                signUpResponse.id,
                signUpResponse.name,
                signUpResponse.skills,
                signUpResponse.token,
                signUpResponse.updatedAt,
                signUpResponse.userRole
            )
        }
    }
}