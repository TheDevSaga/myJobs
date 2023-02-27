package com.example.myjobs.modules.authentication.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobs.data.models.local.User
import com.example.myjobs.data.models.request.ResetPasswordRequest
import com.example.myjobs.data.models.response.ResetPasswordTokenResponse
import com.example.myjobs.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(val authRepository: AuthRepository) : ViewModel() {

    sealed class ResetPasswordEvent {
        object Initial : ResetPasswordEvent()
        object Success : ResetPasswordEvent()
        object Loading : ResetPasswordEvent()
        class Error(val msg: String) : ResetPasswordEvent()
        class WrongData(var passError: Boolean = false, var cPassError: Boolean = false) :
            ResetPasswordEvent()
    }

    private val _stateFlow = MutableStateFlow<ResetPasswordEvent>(ResetPasswordEvent.Initial)
    val stateFlow: StateFlow<ResetPasswordEvent> = _stateFlow

    fun onSubmitTap(password: String, cPassword: String, token: ResetPasswordTokenResponse) {
        var hasError = false
        val wrongData = ResetPasswordEvent.WrongData()
        println(password)
        if (password.length < 6) {
            hasError = true
            wrongData.passError = true
        }
        if (cPassword != password) {
            hasError = true
            wrongData.cPassError = true
        }
        if (hasError) {
            _stateFlow.value = wrongData
            return
        }
        _stateFlow.value = ResetPasswordEvent.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val request = ResetPasswordRequest(
                password, cPassword,
                token.token
            )
            val result = authRepository.resetPassword(request)
            if(result.data?.data!=null){
                authRepository.setUser(User.from(result.data.data))
                _stateFlow.value = ResetPasswordEvent.Success
            }else{
                _stateFlow.value = ResetPasswordEvent.Error(result.message ?:"Something Went Wrong")
            }
        }

    }
}