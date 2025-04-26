package com.zeni.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.zeni.R
import com.zeni.auth.domain.utils.AuthState
import com.zeni.core.domain.utils.extensions.navigateBack
import com.zeni.settings.presentation.components.ProfileViewModel
import com.zeni.settings.presentation.components.SettingsViewModel
import androidx.compose.runtime.getValue
import com.zeni.core.presentation.navigation.ScreenHome
import com.zeni.core.presentation.navigation.ScreenLogin

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navController: NavHostController
) {
    val authState by viewModel.authState.collectAsState()
    val userEmail = if (authState is AuthState.Authenticated) {
        FirebaseAuth.getInstance().currentUser?.email ?: stringResource(R.string.unknown_user)
    } else {
        stringResource(R.string.unknown_user)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController, userEmail, viewModel) }
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.profile_email, userEmail),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { /*todo*/ },
                    shape = RoundedCornerShape(25)
                ) {
                    Text(text = "Config1")
                }
                Button(
                    onClick = { /*todo*/ },
                    shape = RoundedCornerShape(25)
                ) {
                    Text(text = "Config2")
                }
                Button(
                    onClick = { /*todo*/ },
                    shape = RoundedCornerShape(25)
                ) {
                    Text(text = "Config3")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(navController: NavHostController, userEmail: String, viewModel: ProfileViewModel) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.profile_title))
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
                onClick = {
                    viewModel.signout()
                    navController.navigate(ScreenLogin) {
                        popUpTo(0) // Limpia todo el stack de navegación
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null
                )
            }
        }
    )
}