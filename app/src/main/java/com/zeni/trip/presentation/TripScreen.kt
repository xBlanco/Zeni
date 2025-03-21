package com.zeni.trip.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.zeni.R
import com.zeni.core.domain.model.Activity
import com.zeni.core.domain.model.Trip
import com.zeni.core.domain.utils.extensions.navigateBack
import com.zeni.core.presentation.navigation.ScreenUpsertItinerary
import com.zeni.core.presentation.navigation.ScreenUpsertTrip
import com.zeni.itinerary.presentation.components.ActivityInformation
import com.zeni.trip.presentation.components.TripViewModel
import java.time.format.DateTimeFormatter

@Composable
fun TripScreen(
    viewModel: TripViewModel,
    navController: NavHostController
) {
    val trip by viewModel.trip.collectAsStateWithLifecycle()
    val activities by viewModel.activities.collectAsStateWithLifecycle()
    if (trip == null) return

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBar(
                navController = navController,
                trip = trip!!
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.Start
        ) {
            TripData(
                trip = trip!!,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 4.dp,
                        top = 16.dp,
                        end = 8.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.trip_activities_header_text),
                    fontWeight = FontWeight.Bold
                )

                HorizontalDivider(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .clip(MaterialTheme.shapes.extraLarge),
                    thickness = 2.dp
                )

                IconButton(
                    onClick = {
                        navController.navigate(ScreenUpsertItinerary(trip!!.id))
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = LocalContentColor.current.copy(alpha = 0.65f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }

            if (activities.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .weight(weight = 1f),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.Top
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(
                        items = activities
                    ) { activity ->

                        ActivityInformation(
                            activity = activity,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.trip_activities_empty_title),
                        modifier = Modifier
                            .fillMaxWidth(),
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = stringResource(
                            id = R.string.trip_activities_empty_text,
                            trip!!.destination
                        ),
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    navController: NavController,
    trip: Trip
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.trip_title, trip.destination)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = navController::navigateBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(
                onClick = { navController.navigate(ScreenUpsertTrip(trip.id)) }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null
                )
            }
        }
    )
}

@Composable
private fun TripData(
    trip: Trip,
    modifier: Modifier = Modifier
) {
    val locale = LocalConfiguration.current.locale
    val formatter = remember {
        DateTimeFormatter
            .ofPattern("d MMMM yyyy", locale)
            .withLocale(locale)
    }

    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .clipToBounds()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            )
    ) {
        Text(
            text = stringResource(
                id = R.string.trip_destiny,
                trip.destination
            ),
            modifier = Modifier
                .padding(bottom = 4.dp),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(
                id = R.string.trip_start_date,
                trip.startDate.format(formatter)
            ),
            fontSize = 12.sp
        )
        Text(
            text = stringResource(
                id = R.string.trip_end_date,
                trip.endDate.format(formatter)
            ),
            fontSize = 12.sp
        )
    }
}