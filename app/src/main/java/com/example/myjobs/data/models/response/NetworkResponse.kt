package com.example.myjobs.data.models.response

data class NetworkResponse<T>(
    val code: Int,
    val `data`: T?,
    val success: Boolean,
    val message:String?,
    val errors:HashMap<String,Any>
)