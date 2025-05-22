package com.zeni.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.zeni.core.data.database.entities.HotelEntity
import com.zeni.core.data.database.entities.RoomEntity
import com.zeni.core.data.database.entities.RoomImageEntity
import com.zeni.core.data.database.relations.HotelRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelDao {
    @Transaction
    @Query("SELECT * FROM hotel_table")
    fun getHotels(): Flow<List<HotelRelation>>

    @Insert
    suspend fun insertHotel(hotel: HotelEntity)

    @Insert
    suspend fun insertRooms(rooms: List<RoomEntity>)

    @Insert
    suspend fun insertRoomImages(images: List<RoomImageEntity>)
}