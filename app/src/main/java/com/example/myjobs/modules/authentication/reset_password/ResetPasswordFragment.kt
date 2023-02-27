package com.example.myjobs.modules.authentication.reset_password

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.myjobs.databinding.FragmentResetPasswordBinding
import com.example.myjobs.modules.dashboard.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    lateinit var binding: FragmentResetPasswordBinding
    private val viewModel: ResetPasswordViewModel by viewModels()
    private val args: ResetPasswordFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            viewModel.onSubmitTap(
                binding.etPassword.editText!!.text.toString(),
                binding.etCPassword.editText!!.text.toString(),
                args.tokenResponse
            )
        }
        lifecycleScope.launchWhenStarted {
            viewModel.stateFlow.collect { event ->
                when (event) {
                    is ResetPasswordViewModel.ResetPasswordEvent.Error -> {
                        hideLoader()
                        Toast.makeText(requireContext(), event.msg, Toast.LENGTH_LONG).show()
                    }
                    ResetPasswordViewModel.ResetPasswordEvent.Initial -> {}
                    ResetPasswordViewModel.ResetPasswordEvent.Success -> {
                        hideLoader()
                        startActivity(Intent(requireContext(), DashboardActivity::class.java))
                        activity?.finish()
                    }
                    is ResetPasswordViewModel.ResetPasswordEvent.WrongData -> {
                        if (event.passError) {
                            binding.etPassword.error = "Enter 6 Char Password"
                        } else {
                            binding.etPassword.error = null

                        }
                        if (event.cPassError) {
                            binding.etCPassword.error = "Enter Same Password"
                        } else {
                            binding.etCPassword.error = null
                        }
                    }
                    ResetPasswordViewModel.ResetPasswordEvent.Loading -> {
                        showLoader()
                        binding.etPassword.error = null
                        binding.etCPassword.error = null
                    }
                }
            }
        }
    }

    private fun showLoader() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnSubmit.visibility = View.GONE
    }

    private fun hideLoader() {
        binding.progressBar.visibility = View.GONE
        binding.btnSubmit.visibility = View.VISIBLE
    }
}