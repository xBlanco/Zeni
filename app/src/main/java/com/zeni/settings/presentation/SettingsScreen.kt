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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zeni.R
import com.zeni.core.domain.utils.extensions.navigateBack
import com.zeni.settings.domain.utils.Languages
import com.zeni.settings.presentation.components.SettingsViewModel
import org.intellij.lang.annotations.Language

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
                .padding(contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            OutlinedButton(
                onClick = { languageSelectorExpanded = true }
            ) {
                Text(
                    text = stringResource(
                        id = R.string.settings_language_selected,
                        stringResource(language.resId)
                    )
                )
            }

//            LanguageDropdown(
//                selectedLanguage = language,
//                onLanguageSelected = { newLang -> viewModel.updateLanguage(newLang) },
//                availableLanguages = listOf("en", "es", "ca")
//            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Tema oscuro")
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = { viewModel.updateDarkTheme(it) }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Button(
                    onClick = { /*todo*/ },
                    shape = RoundedCornerShape(25)
                )
                {
                    Text(text = "Config1")
                }
                Button(
                    onClick = { /*todo*/ },
                    shape = RoundedCornerShape(25)
                )
                {
                    Text(text = "Config2")
                }
                Button(
                        onClick = { /*todo*/ },
                shape = RoundedCornerShape(25)
                )
                {
                    Text(text = "Config3")
                }
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
        LazyColumn {
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
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = stringResource(language.resId)
        )
    }
}

@Composable
fun LanguageDropdown(
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    availableLanguages: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val languageDisplay = when (selectedLanguage) {
        "es" -> "Español"
        "en" -> "English"
        "ca" -> "Catalan"
        else -> selectedLanguage
    }

    OutlinedTextField(
        value = languageDisplay,
        onValueChange = {},
        label = { Text("Idioma") },
        readOnly = true,
        trailingIcon = {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Mostrar idiomas")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        availableLanguages.forEach { lang ->
            val langName = when (lang) {
                "es" -> "Español"
                "en" -> "English"
                "ca" -> "Catalan"
                else -> lang
            }
            DropdownMenuItem(
                text = { Text(langName) },
                onClick = {
                    onLanguageSelected(lang)
                    expanded = false
                }
            )
        }
    }
}