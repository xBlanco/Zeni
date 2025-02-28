package com.zeni.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zeni.home.presentation.HomeScreen
import com.zeni.home.presentation.components.HomeViewModel
import com.zeni.itinerary.presentation.ItineraryScreen
import com.zeni.itinerary.presentation.components.ItineraryViewModel
import com.zeni.profile.presentation.ProfileScreen
import com.zeni.profile.presentation.components.ProfileViewModel
import com.zeni.settings.presentation.SettingsScreen
import com.zeni.settings.presentation.components.SettingsViewModel
import com.zeni.trip.presentation.TripScreen
import com.zeni.trip.presentation.components.TripViewModel
import kotlin.reflect.KClass

@Composable
fun NavGraph(
    screenInitial: KClass<*> = ScreenHome::class,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = screenInitial,
        modifier = modifier
    ) {
        composable<ScreenHome> {
            val homeViewModel = HomeViewModel()

            HomeScreen(viewModel = homeViewModel)
        }
        composable<ScreenTrip> {
            val tripViewModel = TripViewModel()

            TripScreen(viewModel = tripViewModel)
        }
        composable<ScreenItinerary> {
            val itineraryViewModel = ItineraryViewModel()

            ItineraryScreen(viewModel = itineraryViewModel)
        }
        composable<ScreenSettings> {
            val settingsViewModel = SettingsViewModel()

            SettingsScreen(viewModel = settingsViewModel)
        }
        composable<ScreenProfile> {
            val profileViewModel = ProfileViewModel()

            ProfileScreen(viewModel = profileViewModel, navController = navController)

        }
    }
}