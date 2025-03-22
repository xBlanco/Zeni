package com.zeni.core.domain.repository

import com.zeni.core.domain.model.Activity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZonedDateTime

interface ItineraryRepository {

    fun getActivitiesByTrip(tripId: Long): Flow<List<Activity>>

    fun getActivitiesByDate(date: ZonedDateTime): Flow<List<Activity>>

    fun getActivity(tripId: Long, activityId: Long): Flow<Activity>

    suspend fun addActivity(activity: Activity): Long

    suspend fun existsActivity(tripId: Long, activityId: Long): Boolean

    suspend fun deleteActivity(activity: Activity)
}