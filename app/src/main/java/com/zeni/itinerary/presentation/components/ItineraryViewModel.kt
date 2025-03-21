package com.zeni.itinerary.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeni.core.data.repository.ItineraryRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel(assistedFactory = ItineraryViewModel.ItineraryViewModelFactory::class)
class ItineraryViewModel @AssistedInject constructor(
    @Assisted private val itineraryId: Int,
    private val itineraryRepository: ItineraryRepositoryImpl
) : ViewModel() {

    val itinerary = itineraryRepository.getItineraryItems(itineraryId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = null
        )

    @AssistedFactory
    interface ItineraryViewModelFactory {
        fun create(itineraryId: Int): ItineraryViewModel
    }
}