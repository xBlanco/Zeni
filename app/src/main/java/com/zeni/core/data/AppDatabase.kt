package com.zeni.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zeni.core.data.database.dao.UserDao
import com.zeni.core.data.database.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}