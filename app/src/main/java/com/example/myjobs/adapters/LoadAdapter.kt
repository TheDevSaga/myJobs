package com.example.myjobs.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myjobs.databinding.ItemLoaderBinding

class LoadAdapter : LoadStateAdapter<LoadAdapter.ViewHolder>() {
    inner class ViewHolder(private val viewBinding: ItemLoaderBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(loadState: LoadState) {
            viewBinding.progressBar.isVisible = loadState is LoadState.Loading
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = ItemLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
}