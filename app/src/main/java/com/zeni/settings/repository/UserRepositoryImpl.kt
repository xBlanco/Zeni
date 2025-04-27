package com.zeni.settings.repository

import com.zeni.auth.presentation.register.data.UserDao
import com.zeni.settings.domain.model.UserEntity
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun saveUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override suspend fun isUsernameTaken(username: String): Boolean {
        return userDao.getUserByUsername(username) != null
    }

    override suspend fun getUserByLogin(login: String): UserEntity? {
        return userDao.getUserByLogin(login)
    }
    override suspend fun deleteUserByLogin(login: String) {
        userDao.deleteUserByLogin(login)
    }
}