package com.example.myjobs.di

import android.content.Context
import androidx.room.Room
import com.example.myjobs.data.db.UserDao
import com.example.myjobs.data.db.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext appContext: Context): UserDatabase {
        return Room.databaseBuilder(
            appContext,
            UserDatabase::class.java,
            "RssReader"
        ).build()
    }
    @Provides
    @Singleton
    fun provideUserDao(userDatabase: UserDatabase):UserDao{
        return userDatabase.getUserDao()
    }
}