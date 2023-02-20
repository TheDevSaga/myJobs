package com.example.myjobs.modules.dashboard.applied_jobs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myjobs.R

class AppliedJobsFragment : Fragment() {


    private val viewModel: AppliedJobsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_applied_jobs, container, false)
    }


}