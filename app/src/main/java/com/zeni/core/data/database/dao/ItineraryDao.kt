package com.zeni.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.zeni.core.data.database.entities.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItineraryDao {

    @Query("SELECT * FROM activity_table WHERE trip_id = :tripId ORDER BY date_time DESC")
    fun getActivitiesByTrip(tripId: Long): Flow<List<ActivityEntity>>

    @Query("""
        SELECT * FROM activity_table 
        WHERE date(datetime(date_time/1000, 'unixepoch')) = date(datetime(:timestamp/1000, 'unixepoch')) 
        ORDER BY date_time DESC
    """)
    fun getActivitiesByDate(timestamp: Long): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activity_table WHERE trip_id = :tripId AND id = :activityId")
    fun getActivity(tripId: Long, activityId: Long): Flow<ActivityEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM activity_table WHERE trip_id = :tripId AND id = :activityId)")
    suspend fun existsActivity(tripId: Long, activityId: Long): Boolean

    @Upsert
    suspend fun addActivity(activity: ActivityEntity): Long

    @Upsert
    suspend fun addActivities(activities: List<ActivityEntity>)

    @Delete
    suspend fun deleteActivity(activity: ActivityEntity)
}