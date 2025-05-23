package com.zeni.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "room_table")
data class RoomEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "hotel_id") val hotelId: String,
    @ColumnInfo(name = "room_type") val roomType: String,
    @ColumnInfo(name = "price") val price: Double
)