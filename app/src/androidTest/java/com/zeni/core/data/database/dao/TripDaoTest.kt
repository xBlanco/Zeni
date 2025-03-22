package com.zeni.core.data.database.dao

import androidx.test.filters.SmallTest
import com.zeni.core.data.database.entities.TripEntity
import com.zeni.core.data.mappers.toEntity
import com.zeni.core.data.repository.TripRepositoryImpl
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
class TripDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var tripDao: TripDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testAddTrip() = runBlocking {
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

        val tripId = tripDao.addTrip(trip)
        assert(trip.copy(id = tripId) == tripDao.getTrip(tripId).first().trip)
    }

    @Test
    fun testUpdateTrip() = runBlocking {
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

        val tripId = tripDao.addTrip(trip)
        val updatedTrip = tripDao.getTrip(tripId).first().trip.copy(
            destination = "Updated Destination"
        )

        tripDao.addTrip(updatedTrip)
        assert(updatedTrip.copy(id = tripId) == tripDao.getTrip(tripId).first().trip)
    }

    @Test
    fun testDeleteTrip() = runBlocking {
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

        val tripId = tripDao.addTrip(trip)
        tripDao.deleteTrip(trip.copy(id = tripId))
        assert(!tripDao.existsTrip(tripId))
    }
}