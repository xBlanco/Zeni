package com.zeni.trip.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeni.core.data.repository.TripRepositoryImpl
import com.zeni.core.domain.model.Trip
import com.zeni.trip.domain.use_cases.DeleteTripUseCase
import com.zeni.trip.domain.use_cases.UpsertTripUseCase
import com.zeni.trip.domain.utils.UpsertTripError
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

@HiltViewModel(assistedFactory = UpsertTripViewModel.UpsertTripViewModelFactory::class)
class UpsertTripViewModel @AssistedInject constructor(
    @Assisted private val tripId: Int? = null,
    private val tripRepository: TripRepositoryImpl,
    private val upsertTripUseCase: UpsertTripUseCase,
    private val deleteTripUseCase: DeleteTripUseCase
) : ViewModel() {

    val isEditing: Boolean
        get() = tripId != null

    val destination: StateFlow<String?>
        field = MutableStateFlow(null)
    val isDestinationCorrect: StateFlow<Boolean>
        field = MutableStateFlow(true)
    fun setDestination(value: String) {
        viewModelScope.launch {
            destination.emit(value)

            if (!isDestinationCorrect.value) {
                isDestinationCorrect.emit(value = true)
            }
        }
    }
    suspend fun verifyDestination(): Boolean {
        val isCorrect = destination.value?.isNotBlank() == true
        isDestinationCorrect.emit(value = isCorrect)

        return isCorrect
    }

    val startDate: StateFlow<ZonedDateTime?>
        field = MutableStateFlow(null)
    val isStartDateCorrect: StateFlow<Boolean>
        field = MutableStateFlow(true)
    fun setStartDate(value: ZonedDateTime) {
        viewModelScope.launch {
            startDate.emit(value)

            if (!isStartDateCorrect.value) {
                isStartDateCorrect.emit(value = true)
            }
        }
    }
    suspend fun verifyStartDate(): Boolean {
        val isCorrect = startDate.value != null && startDate.value?.isAfter(ZonedDateTime.now()) == true
        isStartDateCorrect.emit(value = isCorrect)

        return isCorrect
    }

    val endDate: StateFlow<ZonedDateTime?>
        field = MutableStateFlow(null)
    val isEndDateCorrect: StateFlow<Boolean>
        field = MutableStateFlow(true)
    fun setEndDate(value: ZonedDateTime) {
        viewModelScope.launch {
            endDate.emit(value)

            if (!isEndDateCorrect.value) {
                isEndDateCorrect.emit(value = true)
            }
        }
    }
    suspend fun verifyEndDate(): Boolean {
        val isCorrect = endDate.value != null && endDate.value?.isAfter(ZonedDateTime.now()) == true &&
                endDate.value?.isAfter(startDate.value) == true
        isEndDateCorrect.emit(value = isCorrect)

        return isCorrect
    }

    init {
        if (tripId != null) {
            // Edit a trip
            viewModelScope.launch {
                val editingTrip = tripRepository.getTrip(tripId).first()
                destination.emit(editingTrip.destination)
                startDate.emit(editingTrip.startDate)
                endDate.emit(editingTrip.endDate)
            }
        }
    }

    val addingError: StateFlow<UpsertTripError>
        field = MutableStateFlow(UpsertTripError.NONE)
    fun onDismissError() {
        viewModelScope.launch {
            addingError.emit(UpsertTripError.NONE)
        }
    }

    /**
     * Adds a trip to the database and returns the id of the trip
     *
     * @return The id of the trip if the trip was added successfully, null otherwise
     */
    suspend fun addTrip(): Int? {
        val destinationCorrect = verifyDestination()
        val startDateCorrect = verifyStartDate()
        val endDateCorrect = verifyEndDate()
        if (!destinationCorrect || !startDateCorrect || !endDateCorrect) {
            when {
                destination.value.isNullOrBlank() && startDate.value == null && endDate.value == null -> addingError.emit(UpsertTripError.EMPTY_FIELDS)
                startDate.value == null && endDate.value == null -> addingError.emit(UpsertTripError.START_AND_END_DATE_EMPTY)
                destination.value.isNullOrBlank() -> addingError.emit(UpsertTripError.DESTINATION_EMPTY)
                startDate.value == null -> addingError.emit(UpsertTripError.START_DATE_EMPTY)
                endDate.value == null -> addingError.emit(UpsertTripError.END_DATE_EMPTY)
                !startDateCorrect -> addingError.emit(UpsertTripError.START_DATE_BEFORE_TODAY)
                !endDateCorrect -> addingError.emit(UpsertTripError.END_DATE_BEFORE_START_DATE)
            }
            return null
        }

        val tripId = upsertTripUseCase(
            Trip(
                id = tripId ?: 0,
                destination = destination.value!!,
                startDate = startDate.value!!,
                endDate = endDate.value!!,
                itinerary = emptyList()
            )
        )

        return tripId
    }

    suspend fun deleteTrip() {
        deleteTripUseCase(tripRepository.getTrip(tripId!!).first())
    }

    @AssistedFactory
    interface UpsertTripViewModelFactory {
        fun create(tripId: Int?): UpsertTripViewModel
    }
}