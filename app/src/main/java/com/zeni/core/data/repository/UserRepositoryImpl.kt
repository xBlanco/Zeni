package com.zeni.core.data.repository

import com.zeni.core.data.database.dao.UserDao
import com.zeni.core.data.database.entities.UserEntity
import com.zeni.core.domain.repository.UserRepository
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