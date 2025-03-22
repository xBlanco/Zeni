package com.zeni.core.data.repository

import android.util.Log
import com.zeni.core.data.database.dao.ItineraryDao
import com.zeni.core.data.mappers.toDomain
import com.zeni.core.data.mappers.toEntity
import com.zeni.core.domain.model.Activity
import com.zeni.core.domain.repository.ItineraryRepository
import com.zeni.core.util.DatabaseLogger
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
        DatabaseLogger.dbOperation("Getting activities for trip $tripId")
        return try {
            val activitiesFlow = itineraryDao.getActivitiesByTrip(tripId)
                .map { activities -> activities.map { it.toDomain() } }
            DatabaseLogger.dbOperation("Activities retrieved successfully")

            activitiesFlow
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error getting activities: ${e.message}", e)
            throw e
        }
    }

    override fun getActivitiesByDate(date: ZonedDateTime): Flow<List<Activity>> {
        DatabaseLogger.dbOperation("Getting activities for date ${date.toLocalDate()}")
        return try {
            val activitiesFlow = itineraryDao.getActivitiesByDate(date.toInstant().toEpochMilli())
                .map { activities -> activities.map { it.toDomain() } }
            DatabaseLogger.dbOperation("Activities retrieved successfully")

            activitiesFlow
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error getting activities: ${e.message}", e)
            throw e
        }
    }

    override fun getActivity(tripId: Long, activityId: Long): Flow<Activity> {
        DatabaseLogger.dbOperation("Getting activity with id $activityId for trip $tripId")
        return try {
            val activityFlow = itineraryDao.getActivity(tripId, activityId)
                .map { it.toDomain() }
            DatabaseLogger.dbOperation("Activity retrieved successfully")

            activityFlow
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error getting activity: ${e.message}", e)
            throw e
        }
    }

    override suspend fun addActivity(activity: Activity): Long {
        DatabaseLogger.dbOperation("Adding activity to trip ${activity.tripId}")
        return try {
            val id = itineraryDao.addActivity(activity.toEntity())
            DatabaseLogger.dbOperation("Activity added successfully with id: $id")

            id
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error adding activity: ${e.message}", e)
            throw e
        }
    }

    override suspend fun existsActivity(tripId: Long, activityId: Long): Boolean {
        DatabaseLogger.dbOperation("Checking if activity with id $activityId exists for trip $tripId")
        return try {
            itineraryDao.existsActivity(tripId, activityId)
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error checking if activity exists: ${e.message}", e)
            throw e
        }
    }

    override suspend fun deleteActivity(activity: Activity) {
        DatabaseLogger.dbOperation("Deleting activity with id ${activity.id}")
        try {
            itineraryDao.deleteActivity(activity.toEntity())
            DatabaseLogger.dbOperation("Activity deleted successfully")
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error deleting activity: ${e.message}", e)
        }
    }
}