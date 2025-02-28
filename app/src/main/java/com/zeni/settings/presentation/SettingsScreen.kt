package com.zeni.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zeni.R
import com.zeni.core.domain.utils.extensions.navigateBack
import com.zeni.core.presentation.navigation.ScreenAbout
import com.zeni.settings.presentation.components.SettingOption
import com.zeni.settings.presentation.components.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = SettingsViewModel(),
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
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