package com.zeni.core.data.repository

import androidx.test.filters.SmallTest
import com.zeni.core.domain.model.Activity
import com.zeni.core.domain.model.Trip
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.ZonedDateTime
import javax.inject.Inject

@SmallTest
@HiltAndroidTest
class ItineraryRepositoryTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var tripRepository: TripRepositoryImpl

    @Inject
    lateinit var itineraryRepository: ItineraryRepositoryImpl

    var tripId = 0L

    @Before
    fun setup() = runBlocking {
        hiltRule.inject()

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

        tripId = tripRepository.addTrip(trip)
    }

    @Test
    fun testAddItinerary() = runBlocking {
        val activity = Activity(
            id = 0,
            tripId = tripId,
            title = "Test Activity",
            description = "Test Description",
            dateTime = ZonedDateTime.of(
                2022, 1, 1,
                0, 0, 0, 0,
                ZonedDateTime.now().zone
            )
        )

        val itineraryId = itineraryRepository.addActivity(activity)
        assert(activity.copy(id = itineraryId) == itineraryRepository.getActivity(tripId, itineraryId).first())
    }

    @Test
    fun testUpdateItinerary() = runBlocking {
        val activity = Activity(
            id = 0,
            tripId = tripId,
            title = "Test Activity",
            description = "Test Description",
            dateTime = ZonedDateTime.of(
                2022, 1, 1,
                0, 0, 0, 0,
                ZonedDateTime.now().zone
            )
        )

        val itineraryId = itineraryRepository.addActivity(activity)
        val updatedActivity = itineraryRepository.getActivity(tripId, itineraryId).first()
            .copy(
                title = "Updated Activity"
            )

        itineraryRepository.addActivity(updatedActivity)
        assert(updatedActivity == itineraryRepository.getActivity(tripId, itineraryId).first())
    }

    @Test
    fun testDeleteItinerary() = runBlocking {
        val activity = Activity(
            id = 0,
            tripId = tripId,
            title = "Test Activity",
            description = "Test Description",
            dateTime = ZonedDateTime.of(
                2022, 1, 1,
                0, 0, 0, 0,
                ZonedDateTime.now().zone
            )
        )

        val activityId = itineraryRepository.addActivity(activity)
        itineraryRepository.deleteActivity(activity.copy(id = activityId))
        assert(!itineraryRepository.existsActivity(tripId, activityId))
    }
}