package com.zeni.core.domain.repository

import com.zeni.core.data.database.entities.UserEntity

interface UserRepository {
    suspend fun saveUser(user: UserEntity)
    suspend fun isUsernameTaken(username: String): Boolean
    suspend fun getUserByLogin(login: String): UserEntity?
    suspend fun deleteUserByLogin(login: String)
}