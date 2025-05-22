package com.zeni.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_image_table")
data class RoomImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "room_id") val roomId: String,
    @ColumnInfo(name = "image_url") val imageUrl: String
)