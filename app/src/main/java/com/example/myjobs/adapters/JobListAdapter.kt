package com.example.myjobs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myjobs.data.models.response.JobListResponse
import com.example.myjobs.databinding.ItemHeadingBinding
import com.example.myjobs.databinding.ItemJobApplyBinding

class JobListAdapter(val context: Context,var jobListResponse: JobListResponse) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val viewTypeJobItem = 0
    private val viewTypeHeading = 1
    private val viewTypeLoading = 3

    inner class ViewHolder( val viewBinding: ItemJobApplyBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    inner class ViewHeadingHolder(viewBinding: ItemHeadingBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            viewTypeHeading -> {
                val binding =
                    ItemHeadingBinding.inflate(LayoutInflater.from(context), parent, false)
                ViewHeadingHolder(binding)
            }
            else -> {
                val binding =
                    ItemJobApplyBinding.inflate(LayoutInflater.from(context), parent, false)
                ViewHolder(binding)
            }
        }


    }

    override fun getItemCount(): Int {
        return jobListResponse.size+1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.viewBinding.apply {
                val job = jobListResponse[position-1]
                tvTitle.text = job.title
                tvDescription.text = job.description
                tvPlace.text = job.location
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) viewTypeHeading else viewTypeJobItem
    }
}