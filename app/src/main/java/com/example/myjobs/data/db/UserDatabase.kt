package com.example.myjobs.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myjobs.data.models.local.User

@Database(entities = [User::class], version = 1)
abstract class UserDatabase:RoomDatabase() {
    abstract fun getUserDao(): UserDao
}