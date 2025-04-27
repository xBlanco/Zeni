package com.zeni.settings.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
    val unknownUserText = stringResource(R.string.unknown_user)
    val userEmail = if (authState is AuthState.Authenticated) {
        FirebaseAuth.getInstance().currentUser?.email ?: unknownUserText
    } else {
        unknownUserText
    }

    LaunchedEffect(authState) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        Log.d("ProfileScreen", "AuthState: $authState")
        Log.d("ProfileScreen", "CurrentUser: ${currentUser?.email}")

        if (authState is AuthState.Unauthenticated || currentUser == null) {
            Log.d("ProfileScreen", "Usuario no autenticado, redirigiendo al login.")
            navController.navigate(ScreenLogin) {
                popUpTo(0) // Limpia el stack de navegación
            }
        } else {
            Log.d("ProfileScreen", "Usuario autenticado, cargando información.")
            viewModel.loadUserInfo(currentUser.email!!)
        }
    }

    val username by viewModel.username.collectAsState()
    val birthdate by viewModel.birthdate.collectAsState()
    val address by viewModel.address.collectAsState()
    val country by viewModel.country.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val acceptEmails by viewModel.acceptEmails.collectAsState()
    val usernameError by viewModel.usernameError.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController, userEmail, viewModel) }
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.profile_email, userEmail),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = viewModel::setUsername,
                label = { Text(text = stringResource(R.string.profile_username)) },
                modifier = Modifier.fillMaxWidth(),
                isError = usernameError,
                singleLine = true
            )
            if (usernameError) {
                Text(
                    text = stringResource(R.string.error_username_taken),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            OutlinedTextField(
                value = birthdate,
                onValueChange = viewModel::setBirthdate,
                label = { Text(text = stringResource(R.string.profile_birthdate)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = address,
                onValueChange = viewModel::setAddress,
                label = { Text(text = stringResource(R.string.profile_address)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = country,
                onValueChange = viewModel::setCountry,
                label = { Text(text = stringResource(R.string.profile_country)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = viewModel::setPhoneNumber,
                label = { Text(text = stringResource(R.string.profile_phone)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = acceptEmails,
                    onCheckedChange = viewModel::setAcceptEmails
                )
                Text(text = stringResource(R.string.profile_accept_emails))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.saveUserInfo() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.save_button))
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