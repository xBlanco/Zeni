package com.zeni.core.domain.repository

import com.zeni.core.domain.model.Trip
import kotlinx.coroutines.flow.Flow

interface TripRepository {

    fun getTrips(): Flow<List<Trip>>

    fun getTrip(tripId: Int): Flow<Trip>

    suspend fun addTrip(trip: Trip): Int

    suspend fun deleteTrip(trip: Trip)
}