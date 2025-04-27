package com.zeni.auth.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zeni.R
import com.zeni.auth.domain.utils.AuthState
import com.zeni.core.presentation.components.AppIcon
import com.zeni.core.presentation.navigation.ScreenHome
import com.zeni.auth.domain.utils.LoginErrors
import com.zeni.auth.presentation.login.components.LoginViewModel
import com.zeni.core.presentation.navigation.ScreenRegister

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    navController: NavHostController
) {
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val loginButtonEnabled by remember {
        derivedStateOf {
            username.isNotEmpty() && password.isNotEmpty() && loginError == null
        }
    }

    var showAlert by remember { mutableStateOf(false) }


    val authState by viewModel.authState.collectAsState()
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                navController.navigate(ScreenHome) {
                    popUpTo(0) // Limpia tódo el stack de navegación
                }
            }
            is AuthState.Error -> {
                showAlert = true
            }
            else -> Unit
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomBar(
                enabled = loginButtonEnabled,
                onLoginClick = {
                    viewModel.login(username, password)
                    if (viewModel.authState.value is AuthState.Authenticated) {
                        navController.navigate(ScreenHome)
                    }
                },
                onRegisterClick = {
                    navController.navigate(ScreenRegister)
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .imePadding()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppIcon(
                size = 0.70f,
                modifier = Modifier
                    .weight(weight = 1f)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterVertically
                ),
            ) {
                Text(
                    text = stringResource(R.string.login_title),
                    modifier = Modifier
                        .padding(
                            start = 8.dp,
                            bottom = 8.dp
                        ),
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = viewModel::setUsername,
                    label = { Text(text = stringResource(R.string.login_user)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = loginError == LoginErrors.INVALID_USERNAME || loginError == LoginErrors.INVALID_CREDENTIALS,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = viewModel::setPassword,
                    label = { Text(text = stringResource(R.string.login_pass)) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    isError = loginError == LoginErrors.INVALID_PASSWORD || loginError == LoginErrors.INVALID_CREDENTIALS,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions {
                        viewModel.login(username, password)
                        if (viewModel.authState.value is AuthState.Authenticated) {
                            navController.navigate(ScreenHome)
                        } else {
                            showAlert = true
                        }
                    },
                    singleLine = true,
                    shape = MaterialTheme.shapes.large
                )
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

@Composable
private fun BottomBar(
    enabled: Boolean,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .navigationBarsPadding()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onLoginClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = enabled
        ) {
            Text(text = stringResource(R.string.login_btn))
        }
        TextButton(
            onClick = onRegisterClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(R.string.register_btn))
        }
    }
}