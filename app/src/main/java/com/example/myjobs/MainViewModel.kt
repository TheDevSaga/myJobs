package com.example.myjobs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobs.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(authRepository: AuthRepository) : ViewModel() {

    sealed class MainEvent {
        object Initial : MainEvent()
        object Authenticated : MainEvent()
        object New : MainEvent()
    }

    private val _mainStateFlow = MutableStateFlow<MainEvent>(MainEvent.Initial)
    val mainStateFlow: StateFlow<MainEvent> = _mainStateFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val user = authRepository.getUser()
            if (user.value != null) {
                _mainStateFlow.value = MainEvent.Authenticated
            } else {
                _mainStateFlow.value = MainEvent.New
            }
        }
    }

}