package com.example.myjobs.modules.dashboard.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myjobs.adapters.LoadAdapter
import com.example.myjobs.databinding.FragmentHomeBinding
import com.example.myjobs.paging.AvailableJobsPagingAdapter
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
        val availableJobsAdapter = AvailableJobsPagingAdapter()
        binding.rvApplyJobList.adapter =
            availableJobsAdapter.withLoadStateFooter(footer = LoadAdapter())
        binding.rvApplyJobList.layoutManager = LinearLayoutManager(activity)
        binding.rvApplyJobList.setHasFixedSize(true)

        viewModel.jobList.observe(
            viewLifecycleOwner
        ) {
            availableJobsAdapter.submitData(lifecycle, it)
        }
        lifecycleScope.launchWhenStarted {
            availableJobsAdapter.loadStateFlow.collect() {
                if (it.prepend is LoadState.NotLoading && it.prepend.endOfPaginationReached) {
                    binding.progressBar.visibility = View.GONE
                }
                if (it.append is LoadState.NotLoading && it.append.endOfPaginationReached) {
                // Todo Add Empty State
                    //                    emptyState.isVisible = adapter.itemCount < 1
                }
            }
        }

    }


}