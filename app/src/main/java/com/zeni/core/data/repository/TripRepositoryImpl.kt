package com.zeni.core.data.repository

import com.zeni.core.data.database.dao.TripDao
import com.zeni.core.data.mappers.toDomain
import com.zeni.core.data.mappers.toEntity
import com.zeni.core.domain.model.Trip
import com.zeni.core.domain.repository.TripRepository
import com.zeni.core.util.DatabaseLogger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor(
    private val tripDao: TripDao
): TripRepository {

    override fun getTrips(): Flow<List<Trip>> {
        DatabaseLogger.dbOperation("Getting all trips from database")
        return try {
            val tripsFlow = tripDao.getTrips()
                .map { trips -> trips.map { it.toDomain() } }
            DatabaseLogger.dbOperation("Trips retrieved successfully")

            tripsFlow
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error getting trips: ${e.message}", e)
            throw e
        }
    }

    override fun getTrip(tripId: Long): Flow<Trip> {
        DatabaseLogger.dbOperation("Getting trip with id $tripId")
        return try {
            val tripFlow = tripDao.getTrip(tripId)
                .map { it.toDomain() }
            DatabaseLogger.dbOperation("Trip retrieved successfully")

            tripFlow
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error getting trip: ${e.message}", e)
            throw e
        }
    }

    override suspend fun addTrip(trip: Trip): Long {
        DatabaseLogger.dbOperation("Adding trip to ${trip.destination}")
        return try {
            val id = tripDao.addTrip(trip.toEntity())
            DatabaseLogger.dbOperation("Trip added successfully with id: $id")

            id
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error adding trip: ${e.message}", e)
            throw e
        }
    }

    override suspend fun existsTrip(tripId: Long): Boolean {
        DatabaseLogger.dbOperation("Checking if trip exists with id $tripId")
        return try {
            tripDao.existsTrip(tripId)
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error checking if trip exists: ${e.message}", e)
            throw e
        }
    }

    override suspend fun deleteTrip(trip: Trip) {
        DatabaseLogger.dbOperation("Deleting trip with id ${trip.id}")
        try {
            tripDao.deleteTrip(trip.toEntity())
            DatabaseLogger.dbOperation("Trip deleted successfully")
        } catch (e: Exception) {
            DatabaseLogger.dbError("Error deleting trip: ${e.message}", e)
        }
    }
}