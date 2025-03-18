package com.zeni.trip.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeni.core.data.repository.TripRepositoryImpl
import com.zeni.trip.domain.use_cases.DeleteTripUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel(assistedFactory = TripViewModel.TripViewModelFactory::class)
class TripViewModel @AssistedInject constructor(
    @Assisted private val tripId: Int,
    private val tripRepository: TripRepositoryImpl
) : ViewModel() {

    val trip = tripRepository.getTrip(tripId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    @AssistedFactory
    interface TripViewModelFactory {
        fun create(tripId: Int): TripViewModel
    }
}