package com.zeni.core.domain.repository

import com.zeni.core.domain.model.Trip
import kotlinx.coroutines.flow.Flow

interface TripRepository {

    fun getTrips(): Flow<List<Trip>>

    fun getTrip(tripId: Long): Flow<Trip>

    suspend fun addTrip(trip: Trip): Long

    suspend fun existsTrip(tripId: Long): Boolean

    suspend fun deleteTrip(trip: Trip)
}