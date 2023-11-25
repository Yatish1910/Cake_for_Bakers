package com.example.cakeforbakers.localDb

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.cakeforbakers.api.ApiInterface
import com.example.cakeforbakers.api.NetworkConstants
import com.example.cakeforbakers.data.model.RemoteKeys
import com.example.cakeforbakers.data.model.Result

@ExperimentalPagingApi
class RemoteMediatorClass(
    private val apiInterface: ApiInterface,
    private val database: Database
) : RemoteMediator<Int,Result>() {
    private val movieDao = database.movieDao()
    private val remoteKeysDao = database.remoteKeyDoa()
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        return try {

            val currentPage = when(loadType){
                REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: 1
                }
                PREPEND -> {
                    val remoteKeys = getRemoteKeysForFirstItem(state)
                    val prevPage = remoteKeys?.prevKey?:return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys!=null
                    )
                    prevPage
                }
                APPEND -> {
                    val remoteKeys = getRemoteKeysForLastItem(state)
                    val nextPages = remoteKeys?.nextKey ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPages
                }
            }
            val response = apiInterface.getAllMovies(NetworkConstants.APIKEY,currentPage)
            val endOfPagination = response.total_pages == currentPage
            val prevPage = if(currentPage == 1) null else currentPage - 1
            val nextPage = if(endOfPagination) null else currentPage + 1
            database.withTransaction {
                if(loadType == REFRESH){
                    movieDao.deleteMovies()
                    remoteKeysDao.deleteKeys()
                }
                movieDao.addMovies(response.results)
                val keys = response.results.map {movie->
                    RemoteKeys(
                        id = movie.id,
                        prevKey =  prevPage,
                        nextKey = nextPage
                    )
                }
                remoteKeysDao.addAllRemoteKeys(keys)

            }
            MediatorResult.Success(endOfPagination)
        }catch (e: Exception){
            MediatorResult.Error(e)
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state : PagingState<Int,Result>) : RemoteKeys? {
        return state.anchorPosition?.let { position->
            state.closestItemToPosition(position)?.id?.let { id->
                remoteKeysDao.getRemoteKeys(id = id)
            }
        }
    }
    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, Result>) : RemoteKeys? {
        return state.pages.firstOrNull{it.data.isNotEmpty()}?.data?.firstOrNull()
            ?.let {movie->
                remoteKeysDao.getRemoteKeys(id = movie.id)
            }
    }
    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, Result>): RemoteKeys? {
        return state.pages.lastOrNull {it.data.isNotEmpty()}?.data?.lastOrNull()
            ?.let {movie->
                remoteKeysDao.getRemoteKeys(id = movie.id)
            }
    }
}