package com.zeni.core.data.repository

import com.zeni.core.domain.model.Trip
import com.zeni.core.domain.repository.TripRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepositoryImpl @Inject constructor(): TripRepository {
    /**
     * List of trips, TODO: Move to ROOMdb
     */
    private val trips = MutableStateFlow(emptyList<Trip>())

    override fun getTrips() = trips.asStateFlow()

    override fun getTrip(tripId: Int) = trips.map { trips ->
        trips.first { trip -> trip.id == tripId }
    }

    override suspend fun addTrip(trip: Trip): Int {
        if (trip.id == -1) {
            trips.emit(trips.value + trip.copy(id = trips.value.lastOrNull()?.id?.plus(1) ?: 0))
        } else if (trip.id !in trips.value.map { it.id }) {
            trips.emit(trips.value + trip)
        } else {
            trips.emit(trips.value.map { if (it.id == trip.id) trip else it })
        }

        return trips.value.last().id
    }

    override suspend fun deleteTrip(trip: Trip) {
        trips.emit(trips.value - trip)
    }
}