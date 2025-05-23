package com.zeni.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.zeni.R
import com.zeni.home.presentation.components.HomeViewModel
import com.zeni.trip.presentation.DatePickers
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    val hotels = viewModel.hotels.collectAsState().value
    val selectedCity = viewModel.selectedCity.collectAsState().value
//    val startDate = viewModel.startDate.collectAsState().value
//    val endDate = viewModel.endDate.collectAsState().value
    val reservation = viewModel.reservation.collectAsState().value

    var isStartDatePickerOpen by remember { mutableStateOf(value = false) }
    val startDate by viewModel.startDate.collectAsStateWithLifecycle()
    val isStartDateCorrect by viewModel.isStartDateCorrect.collectAsStateWithLifecycle()

    var isEndDatePickerOpen by remember { mutableStateOf(value = false) }
    val endDate by viewModel.endDate.collectAsStateWithLifecycle()
    val isEndDateCorrect by viewModel.isEndDateCorrect.collectAsStateWithLifecycle()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Selección de ciudad
        var expanded by remember { mutableStateOf(false) }
        Box {
            OutlinedButton(onClick = { expanded = true }) {
                Text(selectedCity)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                val cities = listOf("London", "Paris", "Barcelona")
                cities.forEach { city ->
                    DropdownMenuItem(
                        text = { Text(city) },
                        onClick = {
                            viewModel.selectedCity.value = city
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        val context = LocalContext.current
        val calendar = Calendar.getInstance()
        // Selección de fechas (inicio y fin)
        OutlinedButton(
            onClick = { isStartDatePickerOpen = true },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (isStartDateCorrect) ButtonDefaults.outlinedButtonColors().containerColor
                else MaterialTheme.colorScheme.errorContainer,
                contentColor = if (isStartDateCorrect) ButtonDefaults.outlinedButtonColors().contentColor
                else MaterialTheme.colorScheme.error
            )
        ) {
            if (startDate != null) {
                val formatter = DateTimeFormatter.ofPattern("dd/M/yy")
                Text(
                    text = stringResource(
                        id = R.string.select_start_date_text,
                        startDate!!.format(formatter)
                    )
                )
            } else {
                Text(
                    text = stringResource(R.string.select_start_date_text_label),
                    fontSize = 14.sp
                )
            }
        }
        Spacer(Modifier.height(4.dp))

        OutlinedButton(
            onClick = { isEndDatePickerOpen = true },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = if (isEndDateCorrect) ButtonDefaults.outlinedButtonColors().containerColor
                else MaterialTheme.colorScheme.errorContainer,
                contentColor = if (isEndDateCorrect) ButtonDefaults.outlinedButtonColors().contentColor
                else MaterialTheme.colorScheme.error
            )
        ) {
            if (endDate != null) {
                val formatter = DateTimeFormatter.ofPattern("dd/M/yy")
                Text(
                    text = stringResource(
                        id = R.string.select_end_date_text,
                        endDate!!.format(formatter)
                    )
                )
            } else {
                Text(text = stringResource(R.string.select_end_date_text_label))
            }
        }

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                viewModel.fetchHotels(
                    selectedCity,
                    (startDate?.toLocalDate() ?: LocalDate.now()).toString(),
                    (endDate?.toLocalDate() ?: LocalDate.now()).toString()
                )
            },
        ) {
            Text(text = stringResource(R.string.search_hotels))
        }

        Spacer(Modifier.height(16.dp))

        // Lista de hoteles y habitaciones
        LazyColumn {
            items(hotels) { hotel ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(Modifier.padding(8.dp)) {
                        Text(hotel.name, style = MaterialTheme.typography.titleMedium)
                        Text(hotel.address)
                        Image(
                            painter = rememberAsyncImagePainter(hotel.image_url),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(Modifier.height(8.dp))
                        Text("Rooms:")
                        hotel.rooms.take(3).forEach { room ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { viewModel.bookRoom(hotel, room) }
                                    .padding(vertical = 4.dp)
                            ) {
                                Image(
                                    painter = rememberAsyncImagePainter(room.images.firstOrNull() ?: ""),
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.width(8.dp))
                                Column {
                                    Text("Type: ${room.room_type}")
                                    Text("Price: ${room.price}€")
                                }
                                Spacer(Modifier.weight(1f))
                                Button(onClick = { viewModel.bookRoom(hotel, room) }) {
                                    Text("Book")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Mostrar reserva confirmada
        reservation?.let {
            AlertDialog(
                onDismissRequest = { /* Cierra el diálogo */ },
                title = { Text("Reservation Confirmed") },
                text = {
                    Column {
                        Text("Hotel: ${it.hotel.name}")
                        Text("Room: ${it.room.room_type}")
                        Text("Price: ${it.price}€")
                        Text("From: ${it.startDate} To: ${it.endDate}")
                        Image(
                            painter = rememberAsyncImagePainter(it.hotel.image_url),
                            contentDescription = null,
                            modifier = Modifier.height(100.dp)
                        )
                        Image(
                            painter = rememberAsyncImagePainter(it.room.images.firstOrNull() ?: ""),
                            contentDescription = null,
                            modifier = Modifier.height(100.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = { viewModel.reservation.value = null }) {
                        Text("OK")
                    }
                }
            )
        }

        if (isStartDatePickerOpen) {
            DatePickers(
                onClose = { isStartDatePickerOpen = false },
                onSelectedDate = viewModel::setStartDate,
                initialSelectedDateMillis = startDate?.toLocalDate()
                    ?.atStartOfDay(ZoneOffset.UTC)
                    ?.toInstant()
                    ?.toEpochMilli()
            )
        }
        if (isEndDatePickerOpen) {
            DatePickers(
                onClose = { isEndDatePickerOpen = false },
                onSelectedDate = viewModel::setEndDate,
                initialSelectedDateMillis = endDate?.toLocalDate()
                    ?.atStartOfDay(ZoneOffset.UTC)
                    ?.toInstant()
                    ?.toEpochMilli()
            )
        }
    }

}