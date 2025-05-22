package com.zeni.home.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.zeni.core.domain.model.Hotel
import com.zeni.core.domain.model.Room
import com.zeni.core.domain.model.Trip
import com.zeni.core.domain.repository.HotelRepository
import com.zeni.core.domain.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

data class ReservationInfo(
    val reservationId: String,
    val hotel: Hotel,
    val room: Room,
    val price: Double,
    val startDate: ZonedDateTime,
    val endDate: ZonedDateTime
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val hotelRepository: HotelRepository,
    private val tripRepository: TripRepository

) : ViewModel() {

    private val _hotels = MutableStateFlow<List<Hotel>>(emptyList())
    val hotels: StateFlow<List<Hotel>> = _hotels

    val selectedCity = MutableStateFlow("London")
    val reservation = MutableStateFlow<ReservationInfo?>(null)

    private val _startDate = MutableStateFlow<ZonedDateTime?>(null)
    val startDate: StateFlow<ZonedDateTime?> = _startDate

    private val _isStartDateCorrect = MutableStateFlow(true)
    val isStartDateCorrect: StateFlow<Boolean> = _isStartDateCorrect

    private val _endDate = MutableStateFlow<ZonedDateTime?>(null)
    val endDate: StateFlow<ZonedDateTime?> = _endDate

    private val _isEndDateCorrect = MutableStateFlow(true)
    val isEndDateCorrect: StateFlow<Boolean> = _isEndDateCorrect

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchHotels(city: String, startDate: String, endDate: String) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                val result = hotelRepository.getHotels(city, startDate, endDate)
                _hotels.value = result as List<Hotel>
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun bookRoom(hotel: Hotel, room: Room) {
        val reservationId = buildString {
        append(hotel.id)
        append("-")
        append(room.id)
        append("-")
        append(System.currentTimeMillis())
    }
        val info = ReservationInfo(
            reservationId, hotel, room, room.price, startDate.value!!, endDate.value!!
        )
        reservation.value = info
        saveReservationAsTrip(info)
    }

    private fun saveReservationAsTrip(info: ReservationInfo) {
        viewModelScope.launch {
            val trip = Trip(
                name = info.reservationId,
                destination = info.hotel.address,
                startDate = info.startDate,
                endDate = info.endDate
            )
            tripRepository.addTrip(trip)
        }
    }

    fun setStartDate(value: ZonedDateTime) {
        viewModelScope.launch {
            val localDate = value.toLocalDate()
            val selectedStartDate = localDate
                .atTime(LocalTime.MIN)
                .atZone(value.zone)

            _startDate.emit(selectedStartDate)

            if (!_isStartDateCorrect.value) {
                _isStartDateCorrect.emit(true)
            }
        }
    }

    suspend fun verifyStartDate(): Boolean {
        val today = ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS)
        val isCorrect = _startDate.value != null && (_startDate.value == today ||
                _startDate.value?.isAfter(today) == true)
        _isStartDateCorrect.emit(isCorrect)
        return isCorrect
    }

    fun setEndDate(value: ZonedDateTime) {
        viewModelScope.launch {
            val localDate = value.toLocalDate()
            val selectedEndDate = localDate
                .atTime(LocalTime.MAX)
                .atZone(value.zone)

            _endDate.emit(selectedEndDate)

            if (!_isEndDateCorrect.value) {
                _isEndDateCorrect.emit(true)
            }
        }
    }

    suspend fun verifyEndDate(): Boolean {
        val isCorrect = _endDate.value != null && (_endDate.value == _startDate.value ||
                _endDate.value?.isAfter(_startDate.value?.truncatedTo(ChronoUnit.DAYS)) == true)
        _isEndDateCorrect.emit(isCorrect)
        return isCorrect
    }
}