package com.zeni.core.data.hotelsAPI

import com.zeni.core.domain.model.Hotel
import com.zeni.core.domain.model.AvailabilityResponse
import retrofit2.http.*

interface ApiService {
    @GET("hotels/{group_id}/hotels")
    suspend fun getHotels(@Path("group_id") groupId: String): List<Hotel>

    @GET("hotels/{group_id}/availability")
    suspend fun getHotelAvailability(@Path("group_id") groupId: String): AvailabilityResponse

    @POST("hotels/{group_id}/reserve")
    suspend fun reserveHotel(@Path("group_id") groupId: String, @Body body: Any): Any

    @POST("hotels/{group_id}/cancel")
    suspend fun cancelReservation(@Path("group_id") groupId: String, @Body body: Any): Any

    @GET("hotels/{group_id}/reservations")
    suspend fun getReservations(@Path("group_id") groupId: String): Any

    // ADMIN
    @GET("reservations")
    suspend fun getAllReservations(): Any

    @GET("reservations/{res_id}")
    suspend fun getReservation(@Path("res_id") reservationId: String): Any

    @DELETE("reservations/{res_id}")
    suspend fun deleteReservation(@Path("res_id") reservationId: String): Any
}