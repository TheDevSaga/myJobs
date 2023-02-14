package com.example.myjobs.data.models.request

data class SignUpRequest(
    val email: String,
    val password: String,
    val userRole: Int,
    val confirmPassword: String,
    val name: String,
    val skills: String,
)
