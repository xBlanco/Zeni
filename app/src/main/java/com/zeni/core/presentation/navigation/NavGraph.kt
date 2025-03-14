package com.zeni.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zeni.InitialScreen
import com.zeni.Screen
import com.zeni.auth.presentation.login.LoginScreen
import com.zeni.auth.presentation.login.components.LoginViewModel
import com.zeni.auth.presentation.register.RegisterScreen
import com.zeni.auth.presentation.register.components.RegisterViewModel
import com.zeni.settings.presentation.ProfileScreen
import com.zeni.settings.presentation.AboutScreen
import com.zeni.settings.presentation.SettingsScreen
import com.zeni.settings.presentation.TermsScreen
import com.zeni.settings.presentation.components.ProfileViewModel
import com.zeni.settings.presentation.components.SettingsViewModel
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
        composable<ScreenRegister> {
            val viewModel = hiltViewModel<RegisterViewModel>()

            RegisterScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<ScreenLogin> {
            val viewModel = hiltViewModel<LoginViewModel>()

            LoginScreen(
                viewModel = viewModel,
                navController = navController
            )
        }

        composable<ScreenInitial> {
            InitialScreen(
                navController = navController
            )
        }

        composable<ScreenHome> {
            InitialScreen(
                navController = navController,
                initialScreen = Screen.Home.ordinal
            )
        }
        composable<ScreenTrip> {
            InitialScreen(
                navController = navController,
                initialScreen = Screen.Trip.ordinal
            )
        }
        composable<ScreenItinerary> {
            InitialScreen(
                navController = navController,
                initialScreen = Screen.Itinerary.ordinal
            )
        }
        composable<ScreenMore> {
            InitialScreen(
                navController = navController,
                initialScreen = Screen.More.ordinal
            )
        }
        composable<ScreenProfile> {
            val viewModel = hiltViewModel<ProfileViewModel>()

            ProfileScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<ScreenSettings> {
            val viewModel = hiltViewModel<SettingsViewModel>()

            SettingsScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<ScreenTerms>{
            TermsScreen(navController = navController)
        }
        composable<ScreenAbout> {
            AboutScreen(navController = navController)
        }
    }
}