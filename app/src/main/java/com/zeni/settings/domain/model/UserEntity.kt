package com.zeni.settings.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val login: String,
    val username: String,
    val birthdate: String,
    val address: String,
    val country: String,
    val phoneNumber: String,
    val acceptEmails: Boolean
)