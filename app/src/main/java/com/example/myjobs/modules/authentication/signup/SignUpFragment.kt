package com.example.myjobs.modules.authentication.signup

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myjobs.R
import com.example.myjobs.databinding.FragmentSignUpBinding
import com.example.myjobs.modules.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvBack.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        binding.tvLogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }
        binding.btnCandidate.setOnClickListener {
            viewModel.switchUserType(1)
            binding.btnCandidate.apply {
                setBackgroundColor(context.getColor(R.color.blue))
                setIconTintResource(R.color.white)
                setTextColor(context.getColor(R.color.white))
            }
            binding.btnRecruiter.apply {
                setBackgroundColor(context.getColor(R.color.white))
                setIconTintResource(R.color.blue)
                setTextColor(context.getColor(R.color.black))
                strokeWidth = 2
            }
        }
        binding.btnRecruiter.setOnClickListener {
            viewModel.switchUserType(0)
            binding.btnRecruiter.apply {
                setBackgroundColor(context.getColor(R.color.blue))
                setIconTintResource(R.color.white)
                setTextColor(context.getColor(R.color.white))
            }
            binding.btnCandidate.apply {
                setBackgroundColor(context.getColor(R.color.white))
                setIconTintResource(R.color.blue)
                setTextColor(context.getColor(R.color.black))
                strokeWidth = 2
            }
        }
        binding.btnSignUp.setOnClickListener{
            viewModel.signUp(
                binding.etName.editText!!.text.toString(),
                binding.etEmail.editText!!.text.toString(),
                binding.etCreatePass.editText!!.text.toString(),
                binding.etConfirmPass.editText!!.text.toString(),
                binding.etSkills.editText!!.text.toString(),
            )
        }
        lifecycleScope.launchWhenStarted {
            viewModel.signUPFlow.collect { event ->
                when (event) {
                    is SignUpViewModel.SignUpEvent.Error -> {
                        Toast.makeText(context,event.msg,Toast.LENGTH_LONG).show()
                    }
                    SignUpViewModel.SignUpEvent.Initial -> {}
                    SignUpViewModel.SignUpEvent.Loading -> {
                        disableError()
                    }
                    SignUpViewModel.SignUpEvent.Success -> {
                        startActivity(Intent(context, DashboardActivity::class.java))
                        activity?.finish()
                    }
                    is SignUpViewModel.SignUpEvent.WrongData -> {
                        binding.etName.error = "Enter Valid Name"
                        binding.etEmail.error = "Enter Valid Email Address"
                        binding.etCreatePass.error = "Enter password with 6 char min"
                        binding.etConfirmPass.error = "Enter same password"
                        binding.etName.isErrorEnabled =event.nameError
                        binding.etEmail.isErrorEnabled =event.emailError
                        binding.etCreatePass.isErrorEnabled =event.passwordError
                        binding.etConfirmPass.isErrorEnabled =event.cPasswordError
                    }
                }
            }
        }


    }
    private fun disableError(){
        binding.etName.isErrorEnabled =false
        binding.etEmail.isErrorEnabled =false
        binding.etCreatePass.isErrorEnabled =false
        binding.etConfirmPass.isErrorEnabled =false
    }

}