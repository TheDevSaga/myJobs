package com.example.myjobs.modules.authentication.forgot_password

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
import com.example.myjobs.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding

    private val viewModel: ForgotPasswordViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvBack.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }
        binding.btnSubmit.setOnClickListener {
            viewModel.onSubmitTap(binding.etEmail.editText!!.text.toString())
        }
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect(){
                event->
                when(event){
                    is ForgotPasswordViewModel.ForgotPasswordEvent.Error -> {
                        Toast.makeText(requireContext(),event.msg,Toast.LENGTH_LONG).show()
                        hideLoader()

                    }
                    ForgotPasswordViewModel.ForgotPasswordEvent.Initial -> {
                    }
                    ForgotPasswordViewModel.ForgotPasswordEvent.Loading -> {
                        binding.etEmail.error =null
                       showLoader()
                    }
                    is ForgotPasswordViewModel.ForgotPasswordEvent.Success -> {
                        hideLoader()
                        val action = ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToResetPasswordFragment(event.data)
                        findNavController().navigate(action)
                    }
                    ForgotPasswordViewModel.ForgotPasswordEvent.WrongData -> {
                        binding.etEmail.error = "Enter Valid Email"
                    }
                }
            }
        }

    }

    private fun showLoader(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSubmit.visibility = View.GONE
    }
    private fun hideLoader(){
        binding.progressBar.visibility = View.GONE
        binding.btnSubmit.visibility = View.VISIBLE
    }

}