package com.example.myjobs.data.models.request

data class ResetPasswordRequest(
    val confirmPassword: String,
    val password: String,
    val token: String
)