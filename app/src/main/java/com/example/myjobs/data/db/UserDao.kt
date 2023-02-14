package com.example.myjobs.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myjobs.data.models.local.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUser(user: User): Long

    @Query("SELECT * FROM user")
     fun getUser():LiveData<User>

     @Delete
     suspend fun deleteUser(user: User)
}