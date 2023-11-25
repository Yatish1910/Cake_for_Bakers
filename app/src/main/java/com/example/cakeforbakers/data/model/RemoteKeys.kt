package com.example.cakeforbakers.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RemoteKeys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id : Int?,
    val prevKey : Int?,
    val nextKey : Int?
)
