package com.example.cakeforbakers.localDb

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cakeforbakers.data.model.Result

@Dao
interface MovieDao {
    @Query("SELECT * FROM Movie")
    fun getMovie() :PagingSource<Int,Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies : List<Result>)

    @Query("DELETE FROM Movie")
    suspend fun deleteMovies()
}