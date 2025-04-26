package com.zeni.auth.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
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
import com.zeni.auth.presentation.register.components.RegisterViewModel
import com.zeni.core.domain.utils.extensions.navigateBack
import com.zeni.core.presentation.navigation.ScreenLogin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(navController: NavHostController, isRecoveringPassword: Boolean) {
    TopAppBar(
        title = {
            Text(
                text = if (isRecoveringPassword) {
                    stringResource(R.string.recover_password_title)
                } else {
                    stringResource(R.string.register_title)
                }
            )
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
        }
    )
}

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    navController: NavHostController
) {
    val email by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val authState by viewModel.authState.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }
    var isRecoveringPassword by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController = navController, isRecoveringPassword = isRecoveringPassword) },
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
            if (isRecoveringPassword) {
                OutlinedTextField(
                    value = email,
                    onValueChange = viewModel::setUsername,
                    label = { Text(text = stringResource(R.string.register_email)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.extraLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.recoverPassword(email)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = email.isNotEmpty()
                ) {
                    Text(text = stringResource(R.string.recover_password_btn))
                }

                TextButton(
                    onClick = { isRecoveringPassword = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.back_to_register))
                }
            } else {
                OutlinedTextField(
                    value = email,
                    onValueChange = viewModel::setUsername,
                    label = { Text(text = stringResource(R.string.register_email)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.extraLarge
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = viewModel::setPassword,
                    label = { Text(text = stringResource(R.string.register_password)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = true,
                    shape = MaterialTheme.shapes.extraLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.signup(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    enabled = email.isNotEmpty() && password.isNotEmpty()
                ) {
                    Text(text = stringResource(R.string.register_btn))
                }

                TextButton(
                    onClick = { isRecoveringPassword = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.forgot_password))
                }
            }
        }
    }

    if (authState is AuthState.Authenticated) {
        showSuccessDialog = true
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            title = { Text(stringResource(R.string.success_title)) },
            text = { Text(stringResource(R.string.register_success_message)) },
            confirmButton = {
                Button(onClick = {
                    showSuccessDialog = false
                    navController.navigate(ScreenLogin) {
                        popUpTo(0) // Limpia el stack de navegación
                    }
                }) {
                    Text(stringResource(R.string.accept_button))
                }
            }
        )
    }
}