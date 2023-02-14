package com.example.myjobs.modules.authentication.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobs.data.models.local.User
import com.example.myjobs.data.models.request.SignUpRequest
import com.example.myjobs.data.repository.AuthRepository
import com.example.myjobs.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    sealed class SignUpEvent {
        object Initial : SignUpEvent()
        class WrongData(
            var nameError: Boolean = false,
            var emailError: Boolean = false,
            var passwordError: Boolean = false,
            var cPasswordError: Boolean = false,
        ) : SignUpEvent()

        object Loading : SignUpEvent()
        object Success : SignUpEvent()
        class Error(val msg: String) : SignUpEvent()
    }

    private val _signUPFlow = MutableStateFlow<SignUpEvent>(SignUpEvent.Initial)

    val signUPFlow: StateFlow<SignUpEvent> = _signUPFlow
    private var userType: Int = 0

    fun signUp(
        name: String,
        email: String,
        password: String,
        cPassword: String,
        skills: String
    ) {
        var hasError = false
        val wrongData: SignUpEvent.WrongData = SignUpEvent.WrongData()
        if (name.isEmpty()) {
            hasError = true
            wrongData.nameError = true
        }
        if (email.isEmpty()) {
            hasError = true
            wrongData.emailError = true
        }
        if (password.length < 6) {
            hasError = true
            wrongData.passwordError = true
        }
        if (cPassword != password) {
            hasError = true
            wrongData.cPasswordError = true
        }
        if (hasError) {
            _signUPFlow.value = wrongData
            return
        }
        _signUPFlow.value = SignUpEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val signUpRequest = SignUpRequest(email,password,userType, cPassword,name,skills)
            when (val response = repository.signUp(signUpRequest)) {
                is Resource.Error -> _signUPFlow.value =SignUpEvent.Error(response.message!!)
                is Resource.Success -> {
                    setUser(User.from(response.data!!.data!!))
                    _signUPFlow.value = SignUpEvent.Success
                }
            }
        }


    }

    private suspend fun setUser(user: User){
        repository.setUser(user)
    }
    fun switchUserType(type: Int) {
        userType = type
    }
}