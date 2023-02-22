package com.example.myjobs.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myjobs.data.models.response.JobListResponseItem
import com.example.myjobs.databinding.ItemHeadingBinding
import com.example.myjobs.databinding.ItemJobApplyBinding

class JobListAdapter(val context: Context) : ListAdapter<JobListResponseItem,RecyclerView.ViewHolder>(ComparatorDiffUtil()) {
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
        return currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.viewBinding.apply {
                val job = currentList[position]
                tvTitle.text = job.title
                tvDescription.text = job.description
                tvPlace.text = job.location
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) viewTypeHeading else viewTypeJobItem
    }
    class ComparatorDiffUtil : DiffUtil.ItemCallback<JobListResponseItem>() {
        override fun areItemsTheSame(oldItem: JobListResponseItem, newItem: JobListResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: JobListResponseItem, newItem: JobListResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}