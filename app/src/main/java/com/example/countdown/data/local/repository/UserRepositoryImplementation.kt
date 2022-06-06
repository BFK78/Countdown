package com.example.countdown.data.local.repository

import com.example.countdown.data.local.dao.CountdownUserDao
import com.example.countdown.domain.model.UserModel
import com.example.countdown.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImplementation(
    private val userDao: CountdownUserDao
): UserRepository {

    override suspend fun insertUser(user: UserModel) {
        userDao.insertUser(user)
    }

    override suspend fun updateUser(user: UserModel) {
        userDao.updateUser(user = user)
    }

    override suspend fun deleteUser(user: UserModel) {
        userDao.deleteUser(user = user)
    }

    override fun getUser(): Flow<UserModel> {
        return userDao.getUser()
    }

    override fun getUserByName(name: String): Flow<UserModel> {
        return userDao.getUserByName(username = name)
    }




}