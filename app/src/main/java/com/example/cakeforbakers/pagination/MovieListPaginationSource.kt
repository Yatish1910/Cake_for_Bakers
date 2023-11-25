package com.example.cakeforbakers.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cakeforbakers.api.NetworkConstants
import com.example.cakeforbakers.api.ApiInterface
import com.example.cakeforbakers.data.model.MovieData
import com.example.cakeforbakers.data.model.Result

class MovieListPaginationSource(private val apiInterface: ApiInterface) : PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        Log.d("moviesPg","asd")
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val position = params.key ?: 1
            val response = apiInterface.getAllMovies(NetworkConstants.APIKEY,position)
            Log.d("moviesPg","asd")
            LoadResult.Page(
                data = response.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == 1000) null else position + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}