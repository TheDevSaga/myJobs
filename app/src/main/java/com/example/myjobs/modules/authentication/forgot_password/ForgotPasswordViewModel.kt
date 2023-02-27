package com.example.myjobs.modules.authentication.forgot_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjobs.data.models.response.ResetPasswordTokenResponse
import com.example.myjobs.data.repository.AuthRepository
import com.example.myjobs.utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(val repository: AuthRepository):ViewModel() {

    sealed class ForgotPasswordEvent {
        object Initial : ForgotPasswordEvent()
        object Loading : ForgotPasswordEvent()
        object WrongData : ForgotPasswordEvent()
        class Success(val data:ResetPasswordTokenResponse) : ForgotPasswordEvent()
        class Error(val msg:String) : ForgotPasswordEvent()
    }
    private val _stateFlow = MutableStateFlow<ForgotPasswordEvent>(ForgotPasswordEvent.Initial)
    val stateFlow:StateFlow<ForgotPasswordEvent> = _stateFlow

    fun onSubmitTap(email:String){
        if(email.isValidEmail()){
            _stateFlow.value = ForgotPasswordEvent.Loading
            viewModelScope.launch{
                val response = repository.getResetPasswordToken(email)
                if(response.data?.data?.token!=null){
                    _stateFlow.value = ForgotPasswordEvent.Success(response.data.data)
                    _stateFlow.value = ForgotPasswordEvent.Initial
                }else{
                    _stateFlow.value = ForgotPasswordEvent.Error(response.message ?: "Something Went Wrong")
                }
            }
        }else{
            _stateFlow.value = ForgotPasswordEvent.WrongData
        }
    }

}