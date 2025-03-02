package com.zeni.settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * An option of the settings screen.
 *
 * @param title The title of the option.
 * @param onClick The action to perform when the option is clicked, normally navigate.
 * @param modifier The modifier to apply to the option.
 */
@Composable
fun SettingOption(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.large)
            .clipToBounds()
            .background(color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 16.dp
                ),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}