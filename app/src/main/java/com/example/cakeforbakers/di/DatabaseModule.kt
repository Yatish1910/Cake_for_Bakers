package com.example.cakeforbakers.di

import androidx.room.Room
import com.example.cakeforbakers.localDb.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context : android.content.Context) : Database{
        return Room.databaseBuilder(context = context, Database::class.java,"movieDb").build()
    }
}