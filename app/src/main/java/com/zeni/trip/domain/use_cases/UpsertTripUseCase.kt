package com.zeni.trip.domain.use_cases

import com.zeni.core.data.repository.TripRepositoryImpl
import com.zeni.core.domain.model.Trip
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertTripUseCase @Inject constructor(
    private val tripRepository: TripRepositoryImpl
) {
    suspend operator fun invoke(trip: Trip): Long {
        return tripRepository.addTrip(trip)
    }
}