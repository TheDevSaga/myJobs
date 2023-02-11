package com.example.myjobs.modules.authentication.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myjobs.R
import com.example.myjobs.databinding.FragmentLandingBinding


class LandingFragment : Fragment() {

lateinit var binding:FragmentLandingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appBar.btnLoginSignup.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_loginFragment)
        }
        binding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_landingFragment_to_loginFragment)
        }
    }
}