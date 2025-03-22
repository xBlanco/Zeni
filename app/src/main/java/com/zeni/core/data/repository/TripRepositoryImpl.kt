package com.zeni.core.data.repository

import android.util.Log
import com.zeni.core.data.database.dao.TripDao
import com.zeni.core.data.mappers.toDomain
import com.zeni.core.data.mappers.toEntity
import com.zeni.core.domain.model.Trip
import com.zeni.core.domain.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor(
    private val tripDao: TripDao
): TripRepository {

    override fun getTrips(): Flow<List<Trip>> {
        Log.i(TripRepositoryImpl::class.java.simpleName, "Getting all trips from database")
        return tripDao.getTrips()
            .map { trips -> trips.map { it.toDomain() } }
    }

    override fun getTrip(tripId: Long): Flow<Trip> {
        Log.i(TripRepositoryImpl::class.java.simpleName, "Getting trip with id $tripId")
        return tripDao.getTrip(tripId)
            .map { it.toDomain() }
    }

    override suspend fun addTrip(trip: Trip): Long {
        Log.i(TripRepositoryImpl::class.java.simpleName, "Adding trip to ${trip.destination}")

        return tripDao.addTrip(trip.toEntity())
    }

    override suspend fun existsTrip(tripId: Long): Boolean {
        return tripDao.existsTrip(tripId)
    }

    override suspend fun deleteTrip(trip: Trip) {
        Log.i(TripRepositoryImpl::class.java.simpleName, "Deleting trip with id ${trip.id}")
        tripDao.deleteTrip(trip.toEntity())
    }
}