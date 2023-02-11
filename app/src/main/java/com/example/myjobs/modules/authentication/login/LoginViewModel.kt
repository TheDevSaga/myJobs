package com.example.myjobs.modules.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobs.data.models.request.LoginRequest
import com.example.myjobs.data.repository.AuthRepository
import com.example.myjobs.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    sealed class LoginEvent {
        object Initial : LoginEvent()
        class WrongData(val emailError: Boolean,val  passwordError: Boolean) : LoginEvent()
        object Loading : LoginEvent()
        object Success : LoginEvent()
        class Error(val msg: String) : LoginEvent()
    }

    private val _loginFlow = MutableStateFlow<LoginEvent>(LoginEvent.Initial)
    val loginFlow: StateFlow<LoginEvent> = _loginFlow

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _loginFlow.value = LoginEvent.WrongData(email.isEmpty(), password.isEmpty())
            return
        }
        _loginFlow.value = LoginEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val loginRequest = LoginRequest(email, password)

            when (val response = repository.login(loginRequest)) {
                is Resource.Error ->
                    _loginFlow.value = LoginEvent.Error(response.message!!)
                is Resource.Success ->
                    _loginFlow.value = LoginEvent.Success
            }
        }

    }


}