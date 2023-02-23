package com.example.myjobs.data.api

import com.example.myjobs.data.models.response.JobListResponseItem
import com.example.myjobs.data.models.response.NetworkResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JobApi {
    @GET("/api/v1/candidates/jobs")
    suspend fun getAvailableJobs(@Query("page") page:Int=1): Response<NetworkResponse<List<JobListResponseItem>>>

}