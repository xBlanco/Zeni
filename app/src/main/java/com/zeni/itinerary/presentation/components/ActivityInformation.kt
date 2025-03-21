package com.zeni.itinerary.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeni.R
import com.zeni.core.domain.model.Activity
import java.time.format.DateTimeFormatter

@Composable
fun ActivityInformation(
    activity: Activity,
    modifier: Modifier = Modifier
) {
    val locale = LocalConfiguration.current.locale
    val dateFormater = remember {
        DateTimeFormatter
            .ofPattern("d MMMM yyyy", locale)
            .withLocale(locale)
    }
    val timeFormater = remember {
        DateTimeFormatter
            .ofPattern("HH:mm", locale)
            .withLocale(locale)
    }

    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .clipToBounds()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            )
    ) {
        Text(
            text = stringResource(
                id = R.string.trip_activity_name,
                activity.title
            ),
            modifier = Modifier
                .padding(bottom = 4.dp),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = stringResource(
                id = R.string.trip_activity_description,
                activity.description
            ),
            fontSize = 12.sp
        )

        Text(
            text = stringResource(
                id = R.string.trip_activity_date,
                activity.dateTime.format(dateFormater)
            ),
            fontSize = 12.sp
        )

        Text(
            text = stringResource(
                id = R.string.trip_activity_time,
                activity.dateTime.format(timeFormater)
            ),
            fontSize = 12.sp
        )
    }
}