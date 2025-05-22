package com.zeni.core.data.hotelsAPI

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.zeni.core.domain.model.AvailabilityResponse
import com.zeni.core.domain.model.Hotel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HotelApiServiceTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var apiService: ApiService

    @Before
    fun setup() {
        hiltRule.inject()
        apiService = RetrofitClient.instance.create(ApiService::class.java)
    }

    @Test
    fun testGetHotels() = runBlocking {
        val groupId = "G16"
        val hotels: List<Hotel> = apiService.getHotels(groupId)
        assertNotNull(hotels)
        assertTrue(hotels.isNotEmpty())
        assertNotNull(hotels.first().id)
        assertNotNull(hotels.first().name)
    }

    @Test
    fun testGetHotelAvailability() = runBlocking {
        val groupId = "G16"
        val response: AvailabilityResponse = apiService.getHotelAvailability(groupId)
        assertNotNull(response)
        assertNotNull(response.hotel_id)
        assertNotNull(response.available_rooms)
    }
}