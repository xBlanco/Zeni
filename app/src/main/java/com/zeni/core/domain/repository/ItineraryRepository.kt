package com.zeni.core.domain.repository

import com.zeni.core.domain.model.Activity
import kotlinx.coroutines.flow.Flow

interface ItineraryRepository {

    fun getActivitiesByTrip(tripId: Int): Flow<List<Activity>>

    fun getActivity(tripId: Int, activityId: Int): Flow<Activity>

    suspend fun addActivity(itineraryItem: Activity): Int

    suspend fun deleteActivity(itineraryItem: Activity)
}