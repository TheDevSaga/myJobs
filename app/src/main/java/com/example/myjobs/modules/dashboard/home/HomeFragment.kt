package com.example.myjobs.modules.dashboard.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjobs.adapters.JobListAdapter
import com.example.myjobs.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val jobListAdapter = JobListAdapter(requireContext())
        binding.rvApplyJobList.adapter = jobListAdapter
        binding.rvApplyJobList.layoutManager = LinearLayoutManager(activity)

        lifecycleScope.launchWhenStarted {
            viewModel.homeStateFlow.collect() { event ->
                run {
                    when (event) {
                        HomeViewModel.HomeEvent.Loading -> {

                        }
                        HomeViewModel.HomeEvent.LoadingNext -> {

                        }
                        is HomeViewModel.HomeEvent.Success -> {
                            binding.progressBar.visibility = View.GONE
                            jobListAdapter.submitList(event.jobListResponse)
                        }
                    }
                }
            }
        }
    }


}