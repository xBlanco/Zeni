package com.zeni.core.data.repository

import android.util.Log
import com.zeni.core.data.database.dao.ItineraryDao
import com.zeni.core.data.mappers.toDomain
import com.zeni.core.data.mappers.toEntity
import com.zeni.core.domain.model.Activity
import com.zeni.core.domain.repository.ItineraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItineraryRepositoryImpl @Inject constructor(
    private val itineraryDao: ItineraryDao
): ItineraryRepository {

    override fun getActivitiesByTrip(tripId: Long): Flow<List<Activity>> {
        Log.i(ItineraryRepositoryImpl::class.java.simpleName, "Getting activities for trip $tripId")
        return itineraryDao.getActivitiesByTrip(tripId)
            .map { activities -> activities.map { it.toDomain() } }
    }

    override fun getActivitiesByDate(date: ZonedDateTime): Flow<List<Activity>> {
        Log.i(ItineraryRepositoryImpl::class.java.simpleName, "Getting activities for date ${date.toLocalDate()}")
        return itineraryDao.getActivitiesByDate(date.toInstant().toEpochMilli())
            .map { activities -> activities.map { it.toDomain() } }
    }

    override fun getActivity(tripId: Long, activityId: Long): Flow<Activity> {
        Log.i(ItineraryRepositoryImpl::class.java.simpleName, "Getting activity with id $activityId for trip $tripId")
        return itineraryDao.getActivity(tripId, activityId)
            .map { it.toDomain() }
    }

    override suspend fun addActivity(activity: Activity): Long {
        Log.i(ItineraryRepositoryImpl::class.java.simpleName, "Adding activity to trip ${activity.tripId}")


        return itineraryDao.addActivity(activity.toEntity())
    }

    override suspend fun existsActivity(tripId: Long, activityId: Long): Boolean {
        return itineraryDao.existsActivity(tripId, activityId)
    }

    override suspend fun deleteActivity(activity: Activity) {
        Log.i(ItineraryRepositoryImpl::class.java.simpleName, "Deleting activity with id ${activity.id}")
        itineraryDao.deleteActivity(activity.toEntity())
    }
}