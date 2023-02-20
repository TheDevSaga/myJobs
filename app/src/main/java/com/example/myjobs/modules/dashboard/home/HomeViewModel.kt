package com.example.myjobs.modules.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobs.data.models.response.JobListResponse
import com.example.myjobs.data.repository.JobRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(jobRepository: JobRepository) : ViewModel() {

    sealed class HomeEvent{
        object Loading:HomeEvent()
        class Success(val jobListResponse: JobListResponse):HomeEvent()
        object LoadingNext:HomeEvent()
    }
    private val _homeStateFlow = MutableStateFlow<HomeEvent>(HomeEvent.Loading)
    val homeStateFlow:StateFlow<HomeEvent> = _homeStateFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = jobRepository.getAvailableJob()
            if(response.data?.data!=null){
                _homeStateFlow.value = HomeEvent.Success(response.data.data)
            }
        }
    }

}