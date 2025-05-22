package com.zeni.core.data.repository

import com.zeni.core.data.hotelsAPI.ApiService
import com.zeni.core.domain.model.Hotel
import com.zeni.core.domain.model.AvailabilityResponse
import com.zeni.core.domain.repository.HotelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HotelRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HotelRepository {
    override fun getHotels(
        city: String,
        startDate: String,
        endDate: String
    ): Flow<List<Hotel>> {
        TODO("Not yet implemented")
    }

    override fun getHotel(hotelId: String): Flow<Hotel> {
        TODO("Not yet implemented")
    }


}