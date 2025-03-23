package com.zeni.itinerary.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeni.core.data.repository.ItineraryRepositoryImpl
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.ZonedDateTime

@HiltViewModel(assistedFactory = ItineraryViewModel.ItineraryViewModelFactory::class)
class ItineraryViewModel @AssistedInject constructor(
    @Assisted private val itineraryId: Int,
    private val itineraryRepository: ItineraryRepositoryImpl
) : ViewModel() {

    private val currentDateFlow = flow {
        while (true) {
            val now = ZonedDateTime.now()
            emit(now)

            val millisInCurrentMinute = (now.second * 1000) + (now.nano / 1000000)
            val delayUntilNextMinute = 60000L - millisInCurrentMinute

            delay(delayUntilNextMinute)
        }
    }
        .map { date ->
            date.withHour(0).withMinute(0).withSecond(0).withNano(0)
        }
        .distinctUntilChanged()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ZonedDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0)
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val getItineraries = currentDateFlow
        .flatMapLatest { date ->
            itineraryRepository.getActivitiesByDate(date)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = emptyList()
        )

    @AssistedFactory
    interface ItineraryViewModelFactory {
        fun create(itineraryId: Int = 0): ItineraryViewModel
    }
}