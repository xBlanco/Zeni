package com.zeni.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import com.zeni.core.presentation.navigation.currentRoute
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

@Composable
private fun BottomBar(navController: NavHostController) {
    val currentRoute = navController.currentRoute()
    //Boton home, Trip, itinerary, settings
    NavigationBar {
        val isHomeSelected = currentRoute?.endsWith(ScreenHome::class.java.simpleName) == true
        NavigationBarItem(
            selected = isHomeSelected,
            enabled = !isHomeSelected,
            onClick = { navController.navigate(ScreenHome) },
            icon = {
                Icon(
                    imageVector = if (isHomeSelected) Icons.Rounded.Home
                    else Icons.Outlined.Home,
                    contentDescription = null
                )
            }
        )

        val isTripSelected = currentRoute?.endsWith(ScreenTrip::class.java.simpleName) == true
        NavigationBarItem(
            selected = isTripSelected,
            enabled = !isTripSelected,
            onClick = { navController.navigate(ScreenTrip) },
            icon = {
                Icon(
                    painter = if (isTripSelected) painterResource(R.drawable.icon_trip_fill)
                    else painterResource(R.drawable.icon_trip_empty),
                    contentDescription = null
                )
            }
        )

        val isItinerarySelected = currentRoute?.endsWith(ScreenItinerary::class.java.simpleName) == true
        NavigationBarItem(
            selected = isItinerarySelected,
            enabled = !isItinerarySelected,
            onClick = { navController.navigate(ScreenItinerary) },
            icon = {
                Icon(
                    painter = if (isItinerarySelected) painterResource(R.drawable.icon_itinerary_fill)
                    else painterResource(R.drawable.icon_itinerary_empty),
                    contentDescription = null
                )
            }
        )

        val isSettingsSelected = currentRoute?.endsWith(ScreenSettings::class.java.simpleName) == true
        NavigationBarItem(
            selected = isSettingsSelected,
            enabled = !isSettingsSelected,
            onClick = { navController.navigate(ScreenSettings) },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = null
                )
            }
        )
    }
}