package com.example.myjobs.modules.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobs.data.models.local.User
import com.example.myjobs.data.models.request.LoginRequest
import com.example.myjobs.data.repository.AuthRepository
import com.example.myjobs.utils.Resource
import com.example.myjobs.utils.isValidEmail
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
        class WrongData(var emailError: Boolean =false, var passwordError: Boolean=false) : LoginEvent()
        object Loading : LoginEvent()
        object Success : LoginEvent()
        class Error(val msg: String) : LoginEvent()
    }

    private val _loginFlow = MutableStateFlow<LoginEvent>(LoginEvent.Initial)
    val loginFlow: StateFlow<LoginEvent> = _loginFlow

    fun login(email: String, password: String) {
        var hasError = false
        val wrongData = LoginEvent.WrongData()

        if (!email.isValidEmail()) {
            hasError = true
            wrongData.emailError = true
        }
        if (password.length < 6) {
            hasError = true
            wrongData.passwordError = true
        }
        if (hasError) {
            _loginFlow.value = wrongData
            return
        }
        _loginFlow.value = LoginEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val loginRequest = LoginRequest(email, password)

            when (val response = repository.login(loginRequest)) {
                is Resource.Error -> _loginFlow.value = LoginEvent.Error(response.message!!)
                is Resource.Success -> {
                    setUser(User.from(response.data!!.data!!))
                    _loginFlow.value = LoginEvent.Success
                }
            }
        }

    }

    private suspend fun setUser(user: User) {
        repository.setUser(user)
    }

}