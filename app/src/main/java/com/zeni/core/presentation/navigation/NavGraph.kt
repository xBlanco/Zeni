package com.zeni.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            val viewModel = viewModel<RegisterViewModel>()

            RegisterScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<ScreenLogin> {
            val viewModel = viewModel<LoginViewModel>()

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
            val viewModel = viewModel<ProfileViewModel>()

            ProfileScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable<ScreenSettings> {
            val viewModel = viewModel<SettingsViewModel>()

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