package com.zeni.itinerary.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeni.core.domain.model.ItineraryItem
import com.zeni.itinerary.domain.use_cases.use_cases.UpsertItineraryUseCase
import com.zeni.itinerary.domain.use_cases.use_cases.DeleteItineraryUseCase
import com.zeni.itinerary.domain.use_cases.utils.UpsertItineraryError
import com.zeni.trip.domain.utils.UpsertTripError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class UpsertItineraryViewModel(
    private val upsertItineraryUseCase: UpsertItineraryUseCase,
    private val deleteItineraryUseCase: DeleteItineraryUseCase,
    private val tripId: Int
) : ViewModel() {

    private val _description = MutableStateFlow<String?>(null)
    val description: StateFlow<String?> get() = _description

    private val _dateTime = MutableStateFlow<ZonedDateTime?>(null)
    val dateTime: StateFlow<ZonedDateTime?> get() = _dateTime

    fun setDescription(value: String) {
        viewModelScope.launch {
            _description.emit(value)
        }
    }

    fun setDateTime(value: ZonedDateTime) {
        viewModelScope.launch {
            _dateTime.emit(value)
        }
    }

    val addingError: StateFlow<UpsertItineraryError>
        field = MutableStateFlow(UpsertItineraryError.NONE)
    fun onDismissError() {
        viewModelScope.launch {
            addingError.emit(UpsertItineraryError.NONE)
        }
    }

    suspend fun addItinerary(): Int? {
        val descriptionValue = _description.value
        val dateTimeValue = _dateTime.value

        if (descriptionValue.isNullOrBlank() || dateTimeValue == null) {
            // Handle error
            return null
        }

        val itineraryId = upsertItineraryUseCase(
            ItineraryItem(
                id = 0, // Assuming new item
                tripId = tripId,
                title = "Sample Title", // Replace with actual title
                description = descriptionValue,
                dateTime = dateTimeValue
            )
        )

        return itineraryId
    }

    suspend fun deleteItinerary(itineraryItem: ItineraryItem) {
        deleteItineraryUseCase(itineraryItem)
    }
}