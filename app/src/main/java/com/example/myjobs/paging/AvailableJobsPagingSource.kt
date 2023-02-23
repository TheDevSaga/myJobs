package com.example.myjobs.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myjobs.data.api.JobApi
import com.example.myjobs.data.models.response.JobListResponseItem
import kotlin.math.ceil

class AvailableJobsPagingSource (private val jobApi: JobApi) :
    PagingSource<Int, JobListResponseItem>() {
    override fun getRefreshKey(state: PagingState<Int, JobListResponseItem>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, JobListResponseItem> {
        return try {
            val position = params.key ?: 1
            val response = jobApi.getAvailableJobs(position)
            LoadResult.Page(
                data = response.body()!!.data!!,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (ceil((response.body()!!.metadata!!.count).div(20.0)).toInt() == position) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}