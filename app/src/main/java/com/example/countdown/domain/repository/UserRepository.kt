package com.example.countdown.domain.repository

import com.example.countdown.domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(user: UserModel)

    suspend fun updateUser(user: UserModel)

    suspend fun deleteUser(user: UserModel)

    fun getUser(): Flow<UserModel>

    fun getUserByName(name: String): Flow<UserModel>

}