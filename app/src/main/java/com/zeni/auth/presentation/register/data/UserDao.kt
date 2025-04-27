package com.zeni.auth.presentation.register.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zeni.settings.domain.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users WHERE login = :login LIMIT 1")
    suspend fun getUserByLogin(login: String): UserEntity?

    @Query("DELETE FROM users WHERE login = :login")
    suspend fun deleteUserByLogin(login: String)
}