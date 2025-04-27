package com.zeni.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeni.auth.presentation.register.data.UserDao
import com.zeni.settings.domain.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}