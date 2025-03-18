package com.zeni.trip.presentation.components

import androidx.lifecycle.ViewModel
import com.zeni.core.data.repository.TripRepositoryImpl
import com.zeni.trip.domain.use_cases.DeleteTripUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(
    private val tripRepository: TripRepositoryImpl,
    private val deleteTripUseCase: DeleteTripUseCase
) : ViewModel() {

    val trips = tripRepository.getTrips()
}