package com.zeni.core.data.repository

import com.zeni.core.domain.model.Activity
import com.zeni.core.domain.repository.ItineraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItineraryRepositoryImpl @Inject constructor(): ItineraryRepository {
    /**
     * List of itinerary items,
     */
    private val activities = MutableStateFlow(emptyList<Activity>())

    override fun getActivitiesByTrip(tripId: Int): Flow<List<Activity>> {
        return activities.map { items ->
            items
                .filter { it.tripId == tripId }
                .sortedBy { it.dateTime }
        }
    }

    override fun getActivity(tripId: Int, activityId: Int): Flow<Activity> {
        return activities.map { items -> items.first { it.tripId == tripId && it.id == activityId } }
    }

    override suspend fun addActivity(activity: Activity): Int {
        if (activity.id == -1) {
            activities.emit(activities.value + activity.copy(id = activities.value.lastOrNull()?.id?.plus(1) ?: 0))
        } else if (activity.id !in activities.value.map { it.id }) {
            activities.emit(activities.value + activity)
        } else {
            activities.emit(activities.value.map { if (it.id == activity.id) activity else it })
        }

        return activities.value.last().id
    }

    override suspend fun deleteActivity(activity: Activity) {
        activities.emit(activities.value - activity)
    }
}