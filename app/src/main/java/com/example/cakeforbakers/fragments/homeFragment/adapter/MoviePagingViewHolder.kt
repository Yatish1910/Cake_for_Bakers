package com.example.cakeforbakers.fragments.homeFragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cakeforbakers.data.model.MovieData
import com.example.cakeforbakers.data.model.Result
import com.example.cakeforbakers.databinding.SingleMovieItemBinding

class MoviePagingAdapter : PagingDataAdapter<Result,MoviePagingAdapter.MoviePagingViewHolder>(
    COMPARATOR) {

    inner class MoviePagingViewHolder(val singleMovieItemBinding: SingleMovieItemBinding):RecyclerView.ViewHolder(singleMovieItemBinding.root){

    }

    override fun onBindViewHolder(holder: MoviePagingViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null){
            holder.singleMovieItemBinding.movieNameTv.text = item.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePagingViewHolder {
        return MoviePagingViewHolder(
            SingleMovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>(){
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem ==   newItem
            }

        }
    }
}