package com.zeni.itinerary.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.zeni.R
import com.zeni.core.presentation.navigation.ScreenUpsertActivity
import com.zeni.itinerary.presentation.components.ActivityInformation
import com.zeni.itinerary.presentation.components.ItineraryViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.ZonedDateTime

@Composable
fun ItineraryScreen(
    viewModel: ItineraryViewModel,
    navController: NavController
) {
    val activities by viewModel.getItineraries.collectAsStateWithLifecycle()

    if (activities.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(id = R.string.empty_itinerary),
                fontSize = 16.sp
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            state = rememberLazyListState(
                initialFirstVisibleItemIndex = activities.indexOfFirst { activity ->
                    activity.dateTime.isAfter(ZonedDateTime.now())
                }
            ),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.Start
        ) {
            items(
                items = activities,
                key = { activity -> activity.id }
            ) { activity ->

                ActivityInformation(
                    activity = activity,
                    onEditClick = {
                        navController.navigate(
                            ScreenUpsertActivity(activity.tripName, activity.id)
                        )
                    },
                    showTripName = true
                )
            }
        }
    }
}