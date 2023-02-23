package com.example.myjobs.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.myjobs.data.api.JobApi
import com.example.myjobs.paging.AvailableJobsPagingSource
import javax.inject.Inject

class JobRepository @Inject constructor(private val jobApi: JobApi) {

    fun getAvailableJobs() = Pager(
        config = PagingConfig(20),
        pagingSourceFactory = { AvailableJobsPagingSource(jobApi) }
    ).liveData

//    suspend fun getAvailableJob(): Resource<NetworkResponse<List<JobListResponseItem>>> {
//        return try {
//            safeApiCall(Dispatchers.IO) { jobApi.getAvailableJobs() }
//        } catch (e: java.lang.Exception) {
//            Resource.Error(e.message)
//        }
//    }
}