package com.example.countdown.domain.use_cases

import com.example.countdown.domain.model.UserModel
import com.example.countdown.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserUseCase(
    private val userRepository: UserRepository
) {

    suspend fun insertUser(user: UserModel) {
        userRepository.insertUser(user = user)
    }

    suspend fun updateUser(user: UserModel) {
        userRepository.updateUser(user = user)
    }

    suspend fun deleteUser(user: UserModel) {
        userRepository.deleteUser(user = user)
    }

    fun getUserByUsername(name: String): Flow<UserModel> {
        return userRepository.getUserByName(name = name)
    }

    fun getUser(): Flow<UserModel> {
        return userRepository.getUser()
    }

}