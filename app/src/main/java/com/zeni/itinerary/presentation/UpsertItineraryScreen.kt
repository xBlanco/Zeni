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
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
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
import com.zeni.core.presentation.composables.TimePickerDialog
import com.zeni.core.presentation.navigation.ScreenInitial
import com.zeni.itinerary.presentation.components.UpsertItineraryViewModel
import com.zeni.itinerary.domain.use_cases.utils.UpsertItineraryError
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpsertItineraryScreen(
    viewModel: UpsertItineraryViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()

    val title by viewModel.title.collectAsStateWithLifecycle()
    val isTitleCorrect by viewModel.isTitleCorrect.collectAsStateWithLifecycle()

    val description by viewModel.description.collectAsStateWithLifecycle()
    val isDescriptionCorrect by viewModel.isDescriptionCorrect.collectAsStateWithLifecycle()

    val dateTime by viewModel.dateTime.collectAsStateWithLifecycle()
    val isDateTimeCorrect by viewModel.isDateTimeCorrect.collectAsStateWithLifecycle()

    var isDatePickerOpen by remember { mutableStateOf(value = false) }

    var isTimePickerOpen by remember { mutableStateOf(value = false) }
    var dateTimeIsFuture by remember { mutableStateOf(value = true) }
    val timePickerState = rememberTimePickerState(
        initialHour = 12,
        is24Hour = true
    )

    val addingError by viewModel.addingError.collectAsStateWithLifecycle()
    var validationToDelete by remember { mutableStateOf(value = false) }

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
                value = title,
                onValueChange = viewModel::setTitle,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.itinerary_activity_name)) },
                isError = !isTitleCorrect,
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )

            OutlinedTextField(
                value = description,
                onValueChange = viewModel::setDescription,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = stringResource(R.string.itinerary_activity_description)) },
                isError = !isDescriptionCorrect,
                singleLine = true,
                shape = MaterialTheme.shapes.large
            )

            OutlinedButton(
                onClick = { isDatePickerOpen = true },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isDateTimeCorrect) ButtonDefaults.outlinedButtonColors().containerColor
                    else MaterialTheme.colorScheme.errorContainer,
                    contentColor = if (isDateTimeCorrect) ButtonDefaults.outlinedButtonColors().contentColor
                    else MaterialTheme.colorScheme.error
                )
            ) {
                if (dateTime != null) {
                    val formatter = DateTimeFormatter.ofPattern("dd/M/yy HH:mm")
                    Text(text = dateTime!!.format(formatter))
                } else {
                    Text(
                        text = stringResource(R.string.add_itinerary_select_start_date_text_label),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                if (viewModel.isEditing) {
                    FilledIconButton(
                        onClick = { validationToDelete = true },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Delete,
                            contentDescription = null
                        )
                    }
                }

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
                    modifier = Modifier.weight(weight = 1f),
                    enabled = title.isNotBlank() && description.isNotBlank()
                ) {
                    Text(text = stringResource(R.string.add_trip_button))
                }
            }
        }
    }

    if (isDatePickerOpen) {
        DatePickers(
            onDismiss = {
                isDatePickerOpen = false
            },
            onSelectedDate = { selectedDate ->
                isTimePickerOpen = true
                viewModel.setDateTime(value = selectedDate)
            }
        )
    }
    if (isTimePickerOpen) {
        TimePickerDialog(
            onDismissRequest = {
                isTimePickerOpen = false
                viewModel.setDateTime(value = null)
            },
            confirmButton = {
                Button(
                    onClick = {
                        isTimePickerOpen = false
                        viewModel.setDateTime(
                            value = dateTime!!
                                .plusHours(timePickerState.hour.toLong())
                                .plusMinutes(timePickerState.minute.toLong())
                        )
                    },
                    enabled = dateTimeIsFuture
                ) {
                    Text(text = stringResource(id = R.string.accept_button))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        isTimePickerOpen = false
                        viewModel.setDateTime(value = null)
                    }
                ) {
                    Text(text = stringResource(id = R.string.cancel_button))
                }
            }
        ) {
            TimePicker(
                state = timePickerState
            )
        }
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
                Icon(
                    painter = painterResource(R.drawable.icon_alert_empty),
                    contentDescription = null
                )
            },
            text = {
                Text(
                    text = when (addingError) {
                        UpsertItineraryError.EMPTY_FIELDS -> stringResource(R.string.error_creating_trip_fields_empty)
                        UpsertItineraryError.TITLE_EMPTY -> stringResource(R.string.error_creating_title_itinerary_activity)
                        UpsertItineraryError.DESCRIPTION_EMPTY -> stringResource(R.string.error_creating_description_itinerary_activity)
                        UpsertItineraryError.DATE_TIME_EMPTY -> stringResource(R.string.error_creating_datetime_itinerary_activity)
                        UpsertItineraryError.DATE_TIME_BEFORE_NOW -> stringResource(R.string.error_creating_trip_start_before_today)
                        UpsertItineraryError.DATE_TIME_NOT_IN_TRIP_PERIOD -> stringResource(R.string.error_creating_date_not_in_trip_itinerary_activity)
                        UpsertItineraryError.NONE -> ""
                    },
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        )
    }

    if (validationToDelete) {
        AlertDialog(
            onDismissRequest = { validationToDelete = false },
            confirmButton = {
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.deleteItinerary()
                            validationToDelete = false
                            navController.navigate(ScreenInitial) {
                                popUpTo(ScreenInitial) {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(text = stringResource(id = R.string.confirm_delete))
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { validationToDelete = false }
                ) {
                    Text(text = stringResource(id = R.string.cancel_button))
                }
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.icon_alert_empty),
                    contentDescription = null
                )
            },
            title = {
                Text(
                    text = stringResource(R.string.advert_title_deleting_trip),
                    textAlign = TextAlign.Center
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.advert_text_deleting_trip),
                    textAlign = TextAlign.Center
                )
            }
        )
    }

    LaunchedEffect(dateTime) {
        while (true) {
            dateTimeIsFuture = dateTime?.isAfter(ZonedDateTime.now().truncatedTo(ChronoUnit.DAYS)) == true
            delay(timeMillis = 1000)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.add_itinerary_title)) },
        navigationIcon = {
            IconButton(onClick = navController::navigateBack) {
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
private fun DatePickers(
    onDismiss: () -> Unit,
    onSelectedDate: (ZonedDateTime) -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = ZonedDateTime.now().plusDays(1).toLocalDate().atStartOfDay()
            .toInstant(ZoneOffset.UTC).toEpochMilli(),
        selectableDates = SelectableDatesNotPast
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
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
            OutlinedButton(onClick = onDismiss) {
                Text(text = stringResource(id = R.string.cancel_button))
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}