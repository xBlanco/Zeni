package com.zeni.core.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.zeni.core.domain.model.Trip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.ZonedDateTime
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
class TripRepositoryTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var tripRepository: TripRepositoryImpl

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testAddTrip() = runBlocking {
        val trip = Trip(
            id = 0,
            destination = "Test Destination",
            startDate = ZonedDateTime.of(
                2022, 1, 1,
                0, 0, 0, 0,
                ZonedDateTime.now().zone
            ),
            endDate = ZonedDateTime.of(
                2022, 1, 2,
                0, 0, 0, 0,
                ZonedDateTime.now().zone
            )
        )

        val tripId = tripRepository.addTrip(trip)
        assert(trip == tripRepository.getTrip(tripId).first())
    }

    @Test
    fun testUpdateTrip() = runBlocking {
        val trip = Trip(
            id = 0,
            destination = "Test Destination",
            startDate = ZonedDateTime.of(
                2022, 1, 1,
                0, 0, 0, 0,
                ZonedDateTime.now().zone
            ),
            endDate = ZonedDateTime.of(
                2022, 1, 2,
                0, 0, 0, 0,
                ZonedDateTime.now().zone
            )
        )

        val tripId = tripRepository.addTrip(trip)
        val updatedTrip = trip.copy(
            destination = "Updated Destination"
        )

        tripRepository.addTrip(updatedTrip)
        assert(updatedTrip == tripRepository.getTrip(tripId).first())
    }

    @Test
    fun testDeleteTrip() = runBlocking {
        val trip = Trip(
            id = 0,
            destination = "Test Destination",
            startDate = ZonedDateTime.of(
                2022, 1, 1,
                0, 0, 0, 0,
                ZonedDateTime.now().zone
            ),
            endDate = ZonedDateTime.of(
                2022, 1, 2,
                0, 0, 0, 0,
                ZonedDateTime.now().zone
            )
        )

        val tripId = tripRepository.addTrip(trip)
        tripRepository.deleteTrip(trip)
        assert(!tripRepository.existsTrip(tripId))
    }
}