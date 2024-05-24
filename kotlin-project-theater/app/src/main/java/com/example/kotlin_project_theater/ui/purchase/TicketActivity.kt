package com.example.kotlin_project_theater.ui.purchase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlin_project_theater.data.Ticket
import com.example.kotlin_project_theater.ui.theme.AppTheaterTheme

class TicketActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val ticketViewModel = viewModel(modelClass = TicketViewModel::class.java)

            val cinemaName: String = intent.getStringExtra("cinemaName").toString()
            val time: String = intent.getStringExtra("time").toString()
            val date: String = intent.getStringExtra("date").toString()
            val movie: String = intent.getStringExtra("movie").toString()
            val price: Float = intent.getFloatExtra("price", 0.0f)

            ticketViewModel.setCinemaName(cinemaName)
            ticketViewModel.setTime(time)
            ticketViewModel.setDate(date)
            ticketViewModel.setMovieName(movie)
            ticketViewModel.setPrice(price)

            AppTheaterTheme { Surface { TicketScreen(viewModel = ticketViewModel) } }
        }
    }
}

// Выбор типа билета (взрослый/детский)
@Composable
fun TicketScreen(viewModel: TicketViewModel, onPurchaseClicked: () -> Unit = {}) {
    val state = viewModel.state

    var textFieldInput by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Select Ticket Type",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(24.dp))

            var selectedSeat by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }

            TicketOptionItem(selectedSeat, "Seat", true, onChange = { selectedSeat = it })
            TicketOptionItem(email, "Email", false, onChange = { email = it })

            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    "Payment info:",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                var cardNumber by remember { mutableStateOf("") }
                var cvv by remember { mutableStateOf("") }
                TicketOptionItem(
                    cardNumber, "Credit Card Number", true, onChange = { cardNumber = it })
                TicketOptionItem(cvv, "CVV", true, onChange = { cvv = it })
            }

            Spacer(modifier = Modifier.height(16.dp)) // Pushes the button to the bottom

            val cinemasViewModel: CinemasViewModel =
                viewModel(modelClass = CinemasViewModel::class.java)

            Button(
                onClick = {
                    viewModel.addTicket(
                        ticket =
                        Ticket(
                            cinemaName = state.cinemaName,
                            time = state.time,
                            date = state.date,
                            seat = selectedSeat.toInt(),
                            personEmail = email,
                            price = state.price,
                            movie = state.movie
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Text("Confirm")
            }
        }
    }
}

@Composable
fun TicketOptionItem(
    value: String,
    label: String,
    isNumber: Boolean = true,
    onChange: (String) -> Unit
) {

    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        OutlinedTextField(
            value = value,
            onValueChange = onChange,
            label = { Text(label) },
            singleLine = true,
            keyboardOptions =
            KeyboardOptions(
                keyboardType =
                when {
                    isNumber -> KeyboardType.Number
                    else -> KeyboardType.Email
                }
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(name = "TicketOptionsScreen")
@Composable
private fun PreviewTicketOptionsScreen() {
    AppTheaterTheme {
        val cinemasViewModel: CinemasViewModel =
            viewModel(modelClass = CinemasViewModel::class.java)

        Surface {
            // TicketScreen(viewModel = cinemasViewModel)
        }
    }
}
