package com.zeni.itinerary.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.zeni.R
import com.zeni.core.domain.utils.SelectableDatesNotPast
import com.zeni.core.domain.utils.extensions.navigateBack
import com.zeni.core.presentation.navigation.ScreenInitial
import com.zeni.itinerary.presentation.components.UpsertItineraryViewModel
import com.zeni.itinerary.domain.use_cases.utils.UpsertItineraryError
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertItineraryScreen(
    viewModel: UpsertItineraryViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()

    val description by viewModel.description.collectAsStateWithLifecycle()
    val dateTime by viewModel.dateTime.collectAsStateWithLifecycle()

    var isDatePickerOpen by remember { mutableStateOf(false) }
    val addingError by viewModel.addingError.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(navController = navController)
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = description ?: "",
                onValueChange = viewModel::setDescription,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.itinerary_description)) },
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )

            OutlinedButton(
                onClick = { isDatePickerOpen = true },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.outlinedButtonColors()
            ) {
                if (dateTime != null) {
                    val formatter = DateTimeFormatter.ofPattern("dd/M/yy")
                    Text(text = dateTime!!.format(formatter))
                } else {
                    Text(text = stringResource(R.string.select_start_date_text_label), fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            val itineraryId = viewModel.addItinerary()
                            if (itineraryId != null) {
                                navController.navigate(ScreenInitial) {
                                    popUpTo(ScreenInitial) { inclusive = true }
                                }
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = description?.isNotBlank() == true
                ) {
                    Text(text = stringResource(R.string.add_trip_button))
                }
            }
        }
    }

    if (isDatePickerOpen) {
        DatePickers(
            onClose = { isDatePickerOpen = false },
            onSelectedDate = viewModel::setDateTime
        )
    }

    if (addingError != UpsertItineraryError.NONE) {
        AlertDialog(
            onDismissRequest = viewModel::onDismissError,
            confirmButton = {
                Button(onClick = viewModel::onDismissError) {
                    Text(text = stringResource(id = R.string.accept_button))
                }
            },
            icon = {
                Icon(painterResource(R.drawable.icon_alert_empty), contentDescription = null)
            },
            text = {
                Text(
                    text = when (addingError) {
                        UpsertItineraryError.EMPTY_FIELDS -> stringResource(R.string.error_creating_trip_fields_empty)
                        UpsertItineraryError.DATE_TIME_EMPTY -> stringResource(R.string.error_creating_trip_start_and_end_empty)
                        UpsertItineraryError.TITLE_EMPTY -> stringResource(R.string.error_creating_trip_destination_empty)
                        UpsertItineraryError.DATE_TIME_BEFORE_NOW -> stringResource(R.string.error_creating_trip_start_before_today)
                        UpsertItineraryError.NONE -> ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.add_trip_title)) },
        navigationIcon = {
            IconButton(onClick = navController::navigateBack) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DatePickers(
    onClose: () -> Unit,
    onSelectedDate: (ZonedDateTime) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = ZonedDateTime.now().plusDays(1).toLocalDate().atStartOfDay()
            .toInstant(ZoneOffset.UTC).toEpochMilli(),
        selectableDates = SelectableDatesNotPast
    )

    DatePickerDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(
                onClick = {
                    onClose()
                    onSelectedDate(
                        ZonedDateTime.of(
                            LocalDateTime.ofEpochSecond(
                                datePickerState.selectedDateMillis!! / 1000,
                                0,
                                ZoneOffset.UTC
                            ),
                            ZoneId.systemDefault()
                        )
                    )
                },
                enabled = datePickerState.selectedDateMillis != null
            ) {
                Text(text = stringResource(id = R.string.accept_button))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onClose) {
                Text(text = stringResource(id = R.string.cancel_button))
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}