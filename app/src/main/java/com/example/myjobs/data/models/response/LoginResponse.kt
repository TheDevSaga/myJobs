package com.example.myjobs.data.models.response

data class LoginResponse(
    val createdAt: String,
    val email: String,
    val id: String,
    val name: String,
    val skills: String,
    val token: String,
    val updatedAt: String,
    val userRole: Int
)