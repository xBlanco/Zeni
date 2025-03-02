package com.zeni.login.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zeni.R
import com.zeni.core.presentation.navigation.ScreenHome
import com.zeni.login.presentation.components.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavController
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    var showAlert by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.login_title),
                modifier = Modifier
                    .padding(
                        bottom = 6.dp
                    ),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            OutlinedTextField(
                value = username,
                onValueChange = viewModel::setUsername,
                label = { Text(text = stringResource(R.string.login_user)) },
                modifier = Modifier
                    .fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = viewModel::setPassword,
                label = { Text(text = stringResource(R.string.login_pass)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = 6.dp
                    ),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    if (viewModel.verifyCredentials()) {
                        navController.navigate(ScreenHome)
                    } else {
                        showAlert = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.login_btn))
            }
        }
    }

    // Show an alert dialog if the login fails
    if (showAlert) {
        AlertDialog(
            onDismissRequest = { showAlert = false },
            title = { Text(stringResource(R.string.alert_title)) },
            text = { Text(stringResource(R.string.alert_message)) },
            confirmButton = {
                Button(onClick = { showAlert = false }) {
                    Text(stringResource(R.string.alert_btn))
                }
            }
        )
    }
}

