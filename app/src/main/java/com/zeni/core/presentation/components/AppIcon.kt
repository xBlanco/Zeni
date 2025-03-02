package com.zeni.core.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.zeni.R

/**
 * Zeni app icon.
 *
 * @param size The porcentual size of the icon in the screen.
 * @param modifier The modifier to apply to the icon.
 */
@Composable
fun AppIcon(
    size: Float,
    modifier: Modifier = Modifier
) {

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.zeni_logo),
            contentDescription = null,
            modifier = Modifier
                .width(this.maxWidth * size)
                .aspectRatio(ratio = 1f)
        )
    }
}