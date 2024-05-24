package com.example.kotlin_project_theater.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlin_project_theater.data.Ticket
import com.example.kotlin_project_theater.ui.home.HomeViewModel
import com.example.kotlin_project_theater.ui.theme.AppTheaterTheme

class TicketsListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: HomeViewModel = viewModel(modelClass = HomeViewModel::class.java)
            viewModel.getTickets()

            AppTheaterTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TicketsListScreen(viewModel, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TicketsListScreen(homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {

    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        items(homeViewModel.state.tickets) { ticket -> TicketCard(ticket, homeViewModel) }
    }
}

@Composable
fun TicketCard(ticket: Ticket, viewModel: HomeViewModel) {
    var cinemaValue by remember { mutableStateOf(ticket.personEmail) }
    var timeValue by remember { mutableStateOf(ticket.time) }
    var dateValue by remember { mutableStateOf(ticket.date) }
    var seatValue by remember { mutableStateOf(ticket.seat.toString()) }
    var emailValue by remember { mutableStateOf(ticket.personEmail) }
    var priceValue by remember { mutableStateOf(ticket.price.toString()) }
    var movieValue by remember { mutableStateOf(ticket.movie) }

    var allowEdit by remember { mutableStateOf(false) }
    var buttonText by remember {
        mutableStateOf(
            if (allowEdit) {
                "Confirm"
            } else {
                "Edit"
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
        colors =
        CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // заголовок.
            Text("Ticket ${ticket.ticketId}")

            // поля с данными
            DynamicTextField(
                text = cinemaValue,
                allowEdit,
                label = "Cinema",
                onEnter = { cinemaValue = it })
            DynamicTextField(
                text = timeValue, allowEdit, label = "Time", onEnter = { timeValue = it })
            DynamicTextField(
                text = dateValue, allowEdit, label = "Date", onEnter = { dateValue = it })
            DynamicTextField(
                text = seatValue, allowEdit, label = "Seat", onEnter = { seatValue = it })
            DynamicTextField(
                text = emailValue,
                allowEdit,
                label = "Email",
                onEnter = { emailValue = it })
            DynamicTextField(
                text = priceValue,
                allowEdit,
                label = "Price",
                onEnter = { priceValue = it })
            DynamicTextField(
                text = movieValue,
                allowEdit,
                label = "Movie",
                onEnter = { movieValue = it })

            Button(
                onClick = {
                    allowEdit = !allowEdit

                    if (!allowEdit) {
                        viewModel.editTicket(
                            ticket =
                            Ticket(
                                ticketId = ticket.ticketId,
                                cinemaName = cinemaValue,
                                time = timeValue,
                                date = dateValue,
                                seat = seatValue.toInt(),
                                personEmail = emailValue,
                                price = priceValue.toFloat(),
                                movie = movieValue
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text =
                    if (!allowEdit) {
                        "Edit"
                    } else {
                        "Confirm"
                    },
                )
            }

            Button(
                onClick = { viewModel.deleteTicket(ticket = ticket) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                )
            ) {
                Text("Delete")
            }
        }
    }
}

@Composable
fun DynamicTextField(
    text: String,
    isEditable: Boolean = false,
    label: String,
    onEnter: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        TextField(
            value = text,
            onValueChange = onEnter,
            readOnly = if (isEditable) false else true,
            label = { Text(label) },
            singleLine = true,
            colors =
            TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceDim,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            textStyle = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .height(48.dp)
                .shadow(2.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val viewModel: HomeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    viewModel.getTickets()

    AppTheaterTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TicketsListScreen(viewModel, modifier = Modifier.padding(innerPadding))
        }
    }
}
