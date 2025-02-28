package com.zeni

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.zeni.home.presentation.HomeScreen
import com.zeni.itinerary.presentation.ItineraryScreen
import com.zeni.settings.presentation.MoreScreen
import com.zeni.trip.presentation.TripScreen
import kotlinx.coroutines.launch

@Composable
fun InitialScreen(
    navController: NavHostController,
    initialScreen: Int = Screen.Home.ordinal
) {
    val pagerState = rememberPagerState(
        initialPage = initialScreen,
        pageCount = { Screen.entries.size }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            TopBar(pagerState = pagerState)
        },
        bottomBar = {
            BottomBar(
                pagerState = pagerState
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(contentPadding)
        ) { currentIndex ->
            when (currentIndex) {
                Screen.Home.ordinal -> HomeScreen(navController = navController)
                Screen.Trip.ordinal -> TripScreen(navController = navController)
                Screen.Itinerary.ordinal -> ItineraryScreen(navController = navController)
                Screen.More.ordinal -> MoreScreen(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(pagerState: PagerState) {
    val currentScreen by remember {
        derivedStateOf {
            Screen.entries[pagerState.targetPage]
        }
    }

    TopAppBar(
        title = {
            Text(text = stringResource(currentScreen.title))
        },
        actions = {
            // Search icon
            IconButton(
                onClick = { /*TODO()*/ }
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
private fun BottomBar(pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    val currentScreen by remember {
        derivedStateOf {
            Screen.entries[pagerState.targetPage]
        }
    }

    NavigationBar {
        val isHomeSelected = currentScreen == Screen.Home
        NavigationBarItem(
            selected = isHomeSelected,
            onClick = {
                if (!isHomeSelected) {
                    scope.launch {
                        pagerState.scrollToPage(Screen.Home.ordinal)
                    }
                }
            },
            icon = {
                Icon(
                    painter = if (isHomeSelected) painterResource(R.drawable.icon_home_fill)
                    else painterResource(R.drawable.icon_home_empty),
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(R.string.home_title))
            }
        )

        val isTripSelected = currentScreen == Screen.Trip
        NavigationBarItem(
            selected = isTripSelected,
            onClick = {
                if (!isTripSelected) {
                    scope.launch {
                        pagerState.scrollToPage(Screen.Trip.ordinal)
                    }
                }
            },
            icon = {
                Icon(
                    painter = if (isTripSelected) painterResource(R.drawable.icon_trip_fill)
                    else painterResource(R.drawable.icon_trip_empty),
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(R.string.trip_title))
            }
        )

        val isItinerarySelected = currentScreen == Screen.Itinerary
        NavigationBarItem(
            selected = isItinerarySelected,
            onClick = {
                if (!isItinerarySelected) {
                    scope.launch {
                        pagerState.scrollToPage(Screen.Itinerary.ordinal)
                    }
                }
            },
            icon = {
                Icon(
                    painter = if (isItinerarySelected) painterResource(R.drawable.icon_itinerary_fill)
                    else painterResource(R.drawable.icon_itinerary_empty),
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(R.string.itinerary_title))
            }
        )

        val isMoreSelected = currentScreen == Screen.More
        NavigationBarItem(
            selected = isMoreSelected,
            onClick = {
                if (!isMoreSelected) {
                    scope.launch {
                        pagerState.scrollToPage(Screen.More.ordinal)
                    }
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Menu,
                    contentDescription = null
                )
            },
            label = {
                Text(text = stringResource(R.string.more_title))
            }
        )
    }
}

/**
 * Screens represented in the initial screen.
 */
enum class Screen(val title: Int) {
    Home(R.string.home_title),
    Trip(R.string.trip_title),
    Itinerary(R.string.itinerary_title),
    More(R.string.more_title),
    Settings(R.string.settings_title)
}