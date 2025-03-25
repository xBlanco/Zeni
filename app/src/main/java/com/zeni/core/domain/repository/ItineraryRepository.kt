package com.zeni.core.domain.repository

import com.zeni.core.domain.model.Activity
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime

interface ItineraryRepository {

    fun getActivitiesByTrip(tripName: String): Flow<List<Activity>>

    fun getActivitiesByDate(date: ZonedDateTime): Flow<List<Activity>>

    fun getActivity(tripName: String, activityId: Long): Flow<Activity>

    suspend fun addActivity(activity: Activity): Long

    suspend fun existsActivity(tripName: String, activityId: Long): Boolean

    suspend fun deleteActivity(activity: Activity)
}