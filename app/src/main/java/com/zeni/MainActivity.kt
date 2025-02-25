package com.zeni

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.zeni.core.presentation.navigation.NavGraph
import com.zeni.core.presentation.navigation.ScreenHome
import com.zeni.core.presentation.theme.ZeniTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ZeniTheme {
                NavGraph(
                    navController = rememberNavController(),
                    screenInitial = ScreenHome::class
                )
            }
        }
    }
}