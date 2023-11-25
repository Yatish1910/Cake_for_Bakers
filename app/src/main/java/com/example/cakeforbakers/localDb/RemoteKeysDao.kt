package com.example.cakeforbakers.localDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cakeforbakers.data.model.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM RemoteKeys WHERE id =:id" )
    suspend fun getRemoteKeys(id : Int) : RemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys: List<RemoteKeys>)

    @Query("DELETE FROM RemoteKeys")
    suspend fun deleteKeys()
}