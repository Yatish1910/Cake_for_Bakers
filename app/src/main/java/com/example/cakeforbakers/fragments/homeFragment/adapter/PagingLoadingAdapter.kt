package com.example.cakeforbakers.fragments.homeFragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cakeforbakers.databinding.PagingProgressBarBinding

class PagingLoadingAdapter : LoadStateAdapter<PagingLoadingAdapter.LoaderViewHolder>(){

    class LoaderViewHolder(private val binding: PagingProgressBarBinding) :RecyclerView.ViewHolder(binding.root){
        fun bind(loadState: LoadState){
            binding.progressBar.isVisible = loadState is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder(
            PagingProgressBarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}