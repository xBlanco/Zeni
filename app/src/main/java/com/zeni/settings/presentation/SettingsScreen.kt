package com.zeni.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zeni.R
import com.zeni.core.domain.utils.extensions.navigateBack
import com.zeni.settings.domain.utils.Languages
import com.zeni.settings.presentation.components.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavHostController
) {
    val language = viewModel.language
    val isDarkTheme = viewModel.isDarkTheme

    var languageSelectorExpanded by remember { mutableStateOf(false) }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = { TopBar(navController) },
        containerColor = MaterialTheme.colorScheme.background
    ) { contentPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(
                    horizontal = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedButton(
                onClick = { languageSelectorExpanded = true },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(
                        id = R.string.settings_language_selected,
                        stringResource(language.resId)
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.auto_dark_mode)
                )

                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = viewModel::updateAutoDarkTheme
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.dark_mode)
                )

                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = viewModel::updateDarkTheme
                )
            }
        }
    }

    if (languageSelectorExpanded) {
        LanguageSelector(
            onDismiss = { languageSelectorExpanded = false },
            onLanguageSelected = viewModel::updateLanguage,
            modifier = Modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(navController: NavHostController) {
    TopAppBar(
        title = {Text(text = stringResource(R.string.settings_title))},
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSelector(
    onDismiss: () -> Unit,
    onLanguageSelected: (Languages) -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.select_language),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(
                    horizontal = 16.dp
                ),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Top
            )
        ) {
            items(
                items = Languages.entries.toTypedArray(),
                key = { language -> language.code }
            ) { language ->

                LanguageItem(
                    language = language,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onLanguageSelected(language)
                            onDismiss()
                        }
                )
            }
        }
    }
}

@Composable
private fun LanguageItem(
    language: Languages,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .clipToBounds()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = stringResource(language.resId),
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}