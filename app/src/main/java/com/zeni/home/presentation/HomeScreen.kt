package com.zeni.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zeni.R
import com.zeni.core.domain.utils.extensions.navigateBack
import com.zeni.core.presentation.navigation.ScreenHome
import com.zeni.core.presentation.navigation.ScreenItinerary
import com.zeni.core.presentation.navigation.ScreenSettings
import com.zeni.core.presentation.navigation.ScreenTrip
import com.zeni.home.presentation.components.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomBar(navController)
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    //TODO("Not yet implemented")
    //Boton home, Trip, itinerary, settings
    BottomAppBar {
        IconButton(
            onClick = { /*navController.navigate(ScreenHome)*/ }
        ) {
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = null
            )
        }
        IconButton(
            onClick = { navController.navigate(ScreenTrip)}
        ) {
            Icon(
                painterResource(R.drawable.icon_trip_empty),
                contentDescription = null
            )
        }
        IconButton(
            onClick = { navController.navigate(ScreenItinerary)}
        ) {
            Icon(
                painterResource(R.drawable.icon_itinerary_empty),
                contentDescription = null
            )
        }
        IconButton(
            onClick = { navController.navigate(ScreenSettings)}
        ) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = null
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar() {
    TopAppBar(
        title = {
            Text(text = "Home")
        },
        actions = {
            // Search icon
            IconButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            }
        }
    )
}