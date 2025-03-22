package com.zeni.core.domain.repository

import com.zeni.core.domain.model.Activity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ItineraryRepository {

    fun getActivitiesByTrip(tripId: Int): Flow<List<Activity>>

    fun getActivitiesByDate(date: LocalDate): Flow<List<Activity>>

    fun getActivity(tripId: Int, activityId: Int): Flow<Activity>

    suspend fun addActivity(activity: Activity): Int

    suspend fun existsActivity(tripId: Int, activityId: Int): Boolean

    suspend fun deleteActivity(activity: Activity)
}