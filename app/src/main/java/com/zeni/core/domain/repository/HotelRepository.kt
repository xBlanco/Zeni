package com.zeni.core.domain.repository

import com.zeni.core.domain.model.Hotel
import kotlinx.coroutines.flow.Flow

interface HotelRepository {
    fun getHotels(city: String, startDate: String, endDate: String): Flow<List<Hotel>>
    fun getHotel(hotelId: String): Flow<Hotel>
}