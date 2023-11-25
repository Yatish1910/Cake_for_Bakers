package com.example.cakeforbakers.localDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cakeforbakers.data.model.GenreConvertors
import com.example.cakeforbakers.data.model.RemoteKeys
import com.example.cakeforbakers.data.model.Result

@Database(entities = [Result::class,RemoteKeys::class], version = 1)
@TypeConverters(GenreConvertors::class)
abstract class Database : RoomDatabase() {

    abstract fun movieDao() : MovieDao

    abstract fun remoteKeyDoa()  : RemoteKeysDao
}