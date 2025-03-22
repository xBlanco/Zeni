package com.zeni.core.data.database.dao

import androidx.test.filters.SmallTest
import com.zeni.core.data.database.entities.ActivityEntity
import com.zeni.core.data.database.entities.TripEntity
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
class ItineraryDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var tripDao: TripDao

    @Inject
    lateinit var itineraryDao: ItineraryDao

    var tripId = 0L

    @Before
    fun setup() = runBlocking {
        hiltRule.inject()

        val trip = TripEntity(
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
            ),
            coverImageId = null
        )

        tripId = tripDao.addTrip(trip)
    }

    @Test
    fun testAddItinerary() = runBlocking {
        val activity = ActivityEntity(
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

        val itineraryId = itineraryDao.addActivity(activity)
        assert(activity.copy(id = itineraryId) == itineraryDao.getActivity(tripId, itineraryId).first())
    }

    @Test
    fun testUpdateItinerary() = runBlocking {
        val activity = ActivityEntity(
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

        val itineraryId = itineraryDao.addActivity(activity)
        val updatedActivity = itineraryDao.getActivity(tripId, itineraryId).first()
            .copy(
                title = "Updated Activity"
            )

        itineraryDao.addActivity(updatedActivity)
        assert(updatedActivity == itineraryDao.getActivity(tripId, itineraryId).first())
    }

    @Test
    fun testDeleteItinerary() = runBlocking {
        val activity = ActivityEntity(
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

        val activityId = itineraryDao.addActivity(activity)
        itineraryDao.deleteActivity(activity.copy(id = activityId))
        assert(!itineraryDao.existsActivity(tripId, activityId))
    }
}