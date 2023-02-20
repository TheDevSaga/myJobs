package com.example.myjobs.data.repository

import com.example.myjobs.data.api.JobApi
import com.example.myjobs.data.models.response.JobListResponse
import com.example.myjobs.data.models.response.NetworkResponse
import com.example.myjobs.utils.Resource
import kotlinx.coroutines.Dispatchers
import safeApiCall
import javax.inject.Inject

class JobRepository @Inject constructor(private val jobApi: JobApi) {
    suspend fun getAvailableJob(): Resource<NetworkResponse<JobListResponse>> {
        return try {
            safeApiCall(Dispatchers.IO){jobApi.getAvailableJobs()}
        } catch (e:java.lang.Exception) {
            Resource.Error(e.message)
        }
    }
}