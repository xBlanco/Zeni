package com.zeni.settings.repository

import com.zeni.settings.domain.model.UserEntity

interface UserRepository {
    suspend fun saveUser(user: UserEntity)
    suspend fun isUsernameTaken(username: String): Boolean
    suspend fun getUserByLogin(login: String): UserEntity?
    suspend fun deleteUserByLogin(login: String)
}