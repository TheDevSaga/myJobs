package com.example.myjobs.data.models.response

data class JobListResponseItem(
    val createdAt: String,
    val description: String,
    val id: String,
    val location: String,
    val title: String,
    val updatedAt: String
)