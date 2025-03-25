package com.zeni.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.zeni.core.data.database.entities.ActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItineraryDao {

    @Query("SELECT * FROM activity_table WHERE trip_name = :tripName ORDER BY date_time ASC")
    fun getActivitiesByTrip(tripName: String): Flow<List<ActivityEntity>>

    @Query("""
        SELECT * FROM activity_table 
        WHERE date_time >= :startTimeStamp AND date_time <= :endTimeStamp
        ORDER BY date_time ASC
    """)
    fun getActivitiesByDate(startTimeStamp: Long, endTimeStamp: Long): Flow<List<ActivityEntity>>

    @Query("SELECT * FROM activity_table WHERE trip_name = :tripName AND id = :activityId")
    fun getActivity(tripName: String, activityId: Long): Flow<ActivityEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM activity_table WHERE trip_name = :tripName AND id = :activityId)")
    suspend fun existsActivity(tripName: String, activityId: Long): Boolean

    @Upsert
    suspend fun addActivity(activity: ActivityEntity): Long

    @Upsert
    suspend fun addActivities(activities: List<ActivityEntity>)

    @Delete
    suspend fun deleteActivity(activity: ActivityEntity)
}