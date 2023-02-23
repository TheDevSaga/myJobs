package com.example.myjobs.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myjobs.data.models.response.JobListResponseItem
import com.example.myjobs.databinding.ItemJobApplyBinding

class AvailableJobsPagingAdapter :
    PagingDataAdapter<JobListResponseItem, AvailableJobsPagingAdapter.ViewHolder>(COMPARATOR) {
    inner class ViewHolder(val viewBinding: ItemJobApplyBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = getItem(position)
        if (job != null) {
            holder.viewBinding.apply {
                tvTitle.text = job.title
                tvDescription.text = job.description
                tvPlace.text = job.location
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemJobApplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<JobListResponseItem>() {
            override fun areItemsTheSame(
                oldItem: JobListResponseItem,
                newItem: JobListResponseItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: JobListResponseItem,
                newItem: JobListResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}