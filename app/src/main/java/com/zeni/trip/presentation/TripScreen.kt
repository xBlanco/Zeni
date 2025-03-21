package com.zeni.trip.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.zeni.R
import com.zeni.core.domain.model.Trip
import com.zeni.core.domain.utils.extensions.navigateBack
import com.zeni.core.presentation.navigation.ScreenUpsertItinerary
import com.zeni.core.presentation.navigation.ScreenUpsertTrip
import com.zeni.trip.presentation.components.TripViewModel

@Composable
fun TripScreen(
    viewModel: TripViewModel,
    navController: NavHostController
) {
    val trip by viewModel.trip.collectAsStateWithLifecycle()
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
        bottomBar = {
            BottomBar(
                navController = navController,
                trip = trip!!
            )
        }
    ) { contentPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = stringResource(R.string.trip_sample, trip!!.id)
                )
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
        }
    )
}

@Composable
private fun BottomBar(
    navController: NavController,
    trip: Trip
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(
                horizontal = 8.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally
        )
    ) {
        Button(
            onClick = { navController.navigate(ScreenUpsertTrip(trip.id)) },
            modifier = Modifier
                .weight(weight = 1f)
        ) {
            Text(
                text = stringResource(R.string.edit_trip)
            )
        }

        Button(
            onClick = { navController.navigate(ScreenUpsertItinerary(trip.id)) },
            modifier = Modifier
                .weight(weight = 1f)
        ) {
            Text(
                text = stringResource(R.string.add_activity)
            )
        }
    }
}