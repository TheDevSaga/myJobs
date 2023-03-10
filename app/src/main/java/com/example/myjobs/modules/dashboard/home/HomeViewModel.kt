package com.example.myjobs.modules.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.myjobs.data.repository.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(jobRepository: JobRepository) : ViewModel() {

//    sealed class HomeEvent{
//        object Loading:HomeEvent()
//        class Success(val jobListResponse: JobListResponse):HomeEvent()
//        object LoadingNext:HomeEvent()
//    }
//    private val _homeStateFlow = MutableStateFlow<HomeEvent>(HomeEvent.Loading)
//    val homeStateFlow:StateFlow<HomeEvent> = _homeStateFlow
    val jobList = jobRepository.getAvailableJobs().cachedIn(viewModelScope)

//    init {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = jobRepository.getAvailableJob()
//            if(response.data?.data!=null){
////                val list = response.data.data;
////                list.add(0, JobListResponseItem())
////                _homeStateFlow.value = HomeEvent.Success(list)
//            }
//        }
//    }

}