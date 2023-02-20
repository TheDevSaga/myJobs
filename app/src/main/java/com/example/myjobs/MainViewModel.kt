package com.example.myjobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myjobs.data.models.local.User
import com.example.myjobs.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(authRepository: AuthRepository) : ViewModel() {

     val user: LiveData<User>

    init {
        user = authRepository.getUser()
    }

}