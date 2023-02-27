package com.example.myjobs.modules.authentication.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myjobs.R
import com.example.myjobs.databinding.FragmentLoginBinding
import com.example.myjobs.modules.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class LoginFragment : Fragment() {

    lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvCreateAcc.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
        binding.tvBack.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_landingFragment)
        }
        binding.tvForgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        binding.btnLogin.setOnClickListener{
            viewModel.login(
                binding.etEmail.editText?.text.toString(),
                binding.etPassword.editText?.text.toString()
            )
        }
        fun disableError(){
            binding.etEmail.isErrorEnabled =false
            binding.etPassword.isErrorEnabled = false
        }


        lifecycleScope.launchWhenStarted {
            viewModel.loginFlow.collect(){event->
                when(event){
                    is LoginViewModel.LoginEvent.Error -> {
                        Toast.makeText(context,event.msg,Toast.LENGTH_SHORT).show()
                        binding.btnLogin.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                    is LoginViewModel.LoginEvent.Initial -> {
                    }
                    is LoginViewModel.LoginEvent.Loading -> {
                        disableError()
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnLogin.visibility = View.GONE
                    }
                    is LoginViewModel.LoginEvent.Success ->{
                        delay(1000)
                        startActivity(Intent(context,DashboardActivity::class.java))
                        activity?.finish()
                    }
                    is LoginViewModel.LoginEvent.WrongData -> {
                        if(event.emailError){
                            binding.etEmail.error ="Enter valid Email"
                            binding.etEmail.isErrorEnabled = true
                        }else{
                            binding.etEmail.isErrorEnabled =false
                        }
                        if(event.passwordError){
                            binding.etPassword.error ="Enter valid password"
                            binding.etPassword.isErrorEnabled = true
                        }else{
                            binding.etPassword.isErrorEnabled =false
                        }
                    }
                }
            }
        }
    }
}