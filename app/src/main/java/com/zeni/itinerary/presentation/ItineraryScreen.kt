package com.zeni.itinerary.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.zeni.R
import com.zeni.itinerary.presentation.components.ItineraryViewModel

@Composable
fun ItineraryScreen(
    viewModel: ItineraryViewModel = viewModel(),
    navController: NavController
) {
    val itineraryItems by viewModel.itinerary.collectAsState(initial = emptyList())

    if (itineraryItems?.isEmpty() == true) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.Start
        ) {
            itineraryItems?.forEach { item ->
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}