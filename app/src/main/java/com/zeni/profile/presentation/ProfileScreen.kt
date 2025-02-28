package com.zeni.profile.presentation

import android.annotation.SuppressLint
import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.zeni.core.presentation.navigation.ScreenHome
import com.zeni.profile.presentation.components.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, navController: NavHostController) {
    Scaffold (modifier = Modifier.fillMaxSize())
    {
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                onClick = { navController.navigate(ScreenHome) },
                shape = RoundedCornerShape(100)
            )
            {
                Text(text = "Home")
            }
            Box(modifier = Modifier.background(MaterialTheme.colorScheme.primary))
            {
                Text(text = "Profile")
            }
        }
    }
}