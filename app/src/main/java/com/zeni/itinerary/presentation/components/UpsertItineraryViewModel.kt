package com.zeni.itinerary.presentation.components

import android.R.attr.description
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeni.core.data.repository.ItineraryRepositoryImpl
import com.zeni.core.data.repository.TripRepositoryImpl
import com.zeni.core.domain.model.Activity
import com.zeni.core.domain.model.Trip
import com.zeni.itinerary.domain.use_cases.use_cases.UpsertItineraryUseCase
import com.zeni.itinerary.domain.use_cases.use_cases.DeleteItineraryUseCase
import com.zeni.itinerary.domain.use_cases.utils.UpsertItineraryError
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel(assistedFactory = UpsertItineraryViewModel.UpsertItineraryViewModelFactory::class)
class UpsertItineraryViewModel @AssistedInject constructor(
    @Assisted private val tripId: Int,
    @Assisted private val activityId: Int? = null,
    private val tripRepository: TripRepositoryImpl,
    private val itineraryRepository: ItineraryRepositoryImpl,
    private val upsertItineraryUseCase: UpsertItineraryUseCase,
    private val deleteItineraryUseCase: DeleteItineraryUseCase
) : ViewModel() {

    val isEditing: Boolean
        get() = activityId != null

    val trip: StateFlow<Trip?>
        field = MutableStateFlow(value = null)

    val title: StateFlow<String>
        field = MutableStateFlow(value = "")
    val isTitleCorrect: StateFlow<Boolean>
        field = MutableStateFlow(true)
    fun setTitle(value: String) {
        viewModelScope.launch {
            title.emit(value)

            if (!isTitleCorrect.value) {
                isTitleCorrect.emit(value = true)
            }
        }
    }
    suspend fun verifyTitle(): Boolean {
        val isCorrect = title.value.isNotBlank()
        isTitleCorrect.emit(value = isCorrect)

        return isCorrect
    }

    val description: StateFlow<String>
        field = MutableStateFlow(value = "")
    val isDescriptionCorrect: StateFlow<Boolean>
        field = MutableStateFlow(true)
    fun setDescription(value: String) {
        viewModelScope.launch {
            description.emit(value)

            if (!isDescriptionCorrect.value) {
                isDescriptionCorrect.emit(value = true)
            }
        }
    }
    suspend fun verifyDescription(): Boolean {
        val isCorrect = description.value.isNotBlank()
        isDescriptionCorrect.emit(value = isCorrect)

        return isCorrect
    }

    val dateTime: StateFlow<ZonedDateTime?>
        field = MutableStateFlow(value = null)
    val isDateTimeCorrect: StateFlow<Boolean>
        field = MutableStateFlow(true)
    fun setDateTime(value: ZonedDateTime?) {
        viewModelScope.launch {
            dateTime.emit(value)

            if (!isDateTimeCorrect.value) {
                isDateTimeCorrect.emit(value = true)
            }
        }
    }
    suspend fun verifyDateTime(): Boolean {
        val today = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS)
        val isCorrect = dateTime.value != null && dateTime.value!!.isAfter(trip.value!!.startDate) &&
                dateTime.value!!.isBefore(trip.value!!.endDate) && (dateTime.value!! >= today)
        isDateTimeCorrect.emit(value = isCorrect)

        return isCorrect
    }

    init {
        viewModelScope.launch {
            if (isEditing) {
                // Edit the activity
                val editingActivity = itineraryRepository.getActivity(tripId, activityId!!).firstOrNull() ?: return@launch
                title.emit(editingActivity.title)
                description.emit(editingActivity.description)
                dateTime.emit(editingActivity.dateTime)
            }
            trip.emit(tripRepository.getTrip(tripId).first())
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
        val titleCorrect = verifyTitle()
        val descriptionCorrect = verifyDescription()
        val dateTimeCorrect = verifyDateTime()

        if (!titleCorrect || !descriptionCorrect || !dateTimeCorrect) {
            when {
                title.value.isEmpty() && description.value.isEmpty() && description.value.isEmpty() -> {
                    addingError.emit(value = UpsertItineraryError.EMPTY_FIELDS)
                }
                title.value.isEmpty() -> addingError.emit(value = UpsertItineraryError.TITLE_EMPTY)
                description.value.isEmpty() -> addingError.emit(value = UpsertItineraryError.DESCRIPTION_EMPTY)
                dateTime.value == null -> addingError.emit(value = UpsertItineraryError.DATE_TIME_EMPTY)
                dateTime.value!! < ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS) -> addingError.emit(value = UpsertItineraryError.DATE_TIME_BEFORE_TODAY)
                !dateTimeCorrect -> addingError.emit(value = UpsertItineraryError.DATE_TIME_NOT_IN_TRIP_PERIOD)
                else -> addingError.emit(value = UpsertItineraryError.NONE)
            }

            return null
        }

        val itineraryId = upsertItineraryUseCase(
            Activity(
                id = activityId ?: -1,
                tripId = tripId,
                title = title.value,
                description = description.value,
                dateTime = dateTime.value!!
            )
        )

        return itineraryId
    }

    suspend fun deleteItinerary() {
        deleteItineraryUseCase(itineraryRepository.getActivity(tripId, activityId!!).first())
    }

    @AssistedFactory
    interface UpsertItineraryViewModelFactory {
        fun create(tripId: Int, activityId: Int?): UpsertItineraryViewModel
    }
}