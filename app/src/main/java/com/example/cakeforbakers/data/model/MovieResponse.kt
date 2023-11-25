package com.example.assignmentpc.modal

import com.example.cakeforbakers.data.model.MovieData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class MovieResponse(

    @SerializedName("page")
    @Expose
    val page: Int,
    @SerializedName("results")
    @Expose
    val movies: List<MovieData>,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int
)
