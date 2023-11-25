package com.example.cakeforbakers.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cakeforbakers.api.ApiInterface
import com.example.cakeforbakers.data.model.Result
import com.example.cakeforbakers.localDb.Database
import com.example.cakeforbakers.localDb.RemoteMediatorClass
import com.example.cakeforbakers.pagination.MovieListPaginationSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiInterface: ApiInterface,
    private val database: Database ) {

    @OptIn(ExperimentalPagingApi::class)
    fun getMovies() : Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(pageSize = 20 , maxSize = 100),
            remoteMediator = RemoteMediatorClass(apiInterface,database),
            pagingSourceFactory = { database.movieDao().getMovie() }
        ).flow
    }
}