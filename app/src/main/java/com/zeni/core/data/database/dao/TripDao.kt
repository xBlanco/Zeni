package com.zeni.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.zeni.core.data.database.entities.TripEntity
import com.zeni.core.data.database.relations.TripRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {

    @Query("SELECT * FROM trip_table ORDER BY start_date DESC")
    fun getTrips(): Flow<List<TripRelation>>

    @Query("SELECT * FROM trip_table WHERE id = :tripId")
    fun getTrip(tripId: Long): Flow<TripRelation>

    @Query("SELECT EXISTS(SELECT 1 FROM trip_table WHERE id = :tripId)")
    suspend fun existsTrip(tripId: Long): Boolean

    @Upsert
    suspend fun addTrip(trip: TripEntity): Long

    @Delete
    suspend fun deleteTrip(trip: TripEntity)
}