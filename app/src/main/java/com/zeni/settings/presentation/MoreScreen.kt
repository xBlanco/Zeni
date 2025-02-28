package com.zeni.settings.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zeni.R
import com.zeni.core.presentation.navigation.ScreenAbout
import com.zeni.core.presentation.navigation.ScreenMore
import com.zeni.core.presentation.navigation.ScreenProfile
import com.zeni.core.presentation.navigation.ScreenSettings
import com.zeni.settings.presentation.components.SettingOption
import com.zeni.settings.presentation.components.SettingsViewModel

@Composable
fun MoreScreen(
//    viewModel: SettingsViewModel = SettingsViewModel(),
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Option(
            title = stringResource(R.string.user_title),
            onClick = { navController.navigate(ScreenProfile) },
            modifier = Modifier
        )
        Option(
            title = stringResource(R.string.settings_title),
            onClick = { navController.navigate(ScreenSettings) },
            modifier = Modifier
        )
        Option(
            title = stringResource(R.string.about_zeni),
            onClick = { navController.navigate(ScreenAbout) },
            modifier = Modifier
        )
    }
}

@Composable
private fun Option(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SettingOption(
        title = title,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
    )
}