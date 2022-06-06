package com.example.countdown.data.local.dao

import androidx.room.*
import com.example.countdown.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CountdownUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUser(user: UserModel)

    @Delete
    suspend fun deleteUser(user: UserModel)

    @Query("SELECT * FROM USERMODEL WHERE name = :username")
    fun getUserByName(username: String): Flow<UserModel>

    @Query("SELECT * FROM USERMODEL WHERE id = 1")
    fun getUser(): Flow<UserModel>
}