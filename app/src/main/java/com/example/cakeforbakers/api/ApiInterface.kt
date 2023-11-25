package com.example.cakeforbakers.api

import com.example.cakeforbakers.data.model.MovieData
import com.example.cakeforbakers.data.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("3/trending/movie/day?language=en-US")
    suspend fun getAllMovies(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): MovieList

}