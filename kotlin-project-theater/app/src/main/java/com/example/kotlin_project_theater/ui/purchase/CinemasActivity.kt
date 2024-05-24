package com.example.kotlin_project_theater.ui.purchase

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlin_project_theater.R
import com.example.kotlin_project_theater.data.Cinema
import com.example.kotlin_project_theater.data.TableData
import com.example.kotlin_project_theater.ui.theme.AppTheaterTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CinemasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val movieId: Int = intent.getIntExtra("movieId", 1)
            val cinemasViewModel: CinemasViewModel =
                viewModel(modelClass = CinemasViewModel::class.java)

            val ticketsViewModel: TicketViewModel =
                viewModel(modelClass = TicketViewModel::class.java)

            cinemasViewModel.setMovieById(movieId)

            // установка имени и цены фильма для state.
            ticketsViewModel.setMovieName(cinemasViewModel.state.movie.title)
            ticketsViewModel.setPrice(cinemasViewModel.state.movie.price)

            AppTheaterTheme { Surface { CinemasScreen(ticketsViewModel) } }
        }
    }
}

@Composable
fun CinemasScreen(viewModel: TicketViewModel) {
    val state = viewModel.state

    Column {

        // Заголовок страницы.
        MovieTitle(title = state.movie)

        HorizontalDivider(Modifier.padding(vertical = 8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
        ) {
            items(TableData.cinemasList) { cinema -> CinemaCard(cinema, viewModel) }
        }
    }
}

@Composable
fun CinemaCard(cinema: Cinema, viewModel: TicketViewModel, modifier: Modifier = Modifier) {
    Card(modifier = modifier.padding(vertical = 16.dp)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {

            // Название и адрес кинотеатра.
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // Название театра.
                Text(text = cinema.name, style = MaterialTheme.typography.headlineMedium)

                // Адрес.
                Text(
                    text = "Address: ${cinema.location}",
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            // Разделитель между секциями.
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )

            // Выбор даты
            Text(text = "Date", style = MaterialTheme.typography.headlineSmall)
            val today = LocalDate.now()
            val dateChoices =
                listOf(
                    today.plusDays(1.toLong()).format(DateTimeFormatter.ofPattern("MMMM.d")),
                    today.plusDays(2.toLong()).format(DateTimeFormatter.ofPattern("MMMM.d")),
                    today.plusDays(3.toLong()).format(DateTimeFormatter.ofPattern("MMMM.d"))
                )
            var selectedDate by remember { mutableStateOf("") }
            RadioButtonsRow(dateChoices, onValueSelected = { selectedDate = it })

            Spacer(modifier = Modifier.size(12.dp))

            // выбор времени
            Text(text = "Time", style = MaterialTheme.typography.headlineSmall)
            val timeChoices = listOf("12:00 PM", "03:00 PM", "09:00 PM")
            var selectedTime by remember { mutableStateOf("") }
            RadioButtonsRow(timeChoices, onValueSelected = { selectedTime = it })

            val context = LocalContext.current
            val intent = Intent(context, TicketActivity::class.java)
            Button(
                onClick = {
                    intent.putExtra("cinemaName", cinema.name)
                    intent.putExtra("time", selectedTime)
                    intent.putExtra("date", selectedDate)
                    intent.putExtra("movie", viewModel.state.movie)
                    intent.putExtra("price", viewModel.state.price)

                    /*                 viewModel.setTime(selectedTime)
                    viewModel.setDate(selectedDate)
                    viewModel.setCinemaName(cinema.name) */

                    context.startActivity(intent)
                }) {
                Text(text = "Confirm")
            }
        }
    }
}

@Composable
fun RadioButtonsRow(
    options: List<String>,
    onValueSelected: (String) -> Unit,
    title: String = "sad"
) {
    val selectedValue = remember { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = option)
                RadioButton(
                    selected = selectedValue.value == option,
                    onClick = {
                        selectedValue.value = option
                        onValueSelected(option)
                    })
            }
        }
    }
}

// строка с кнопками выбора времени.
@Composable
fun PickTimeButtonsRow() {
    val timeChoices = listOf("12:00 PM", "03:00 PM", "09:00 PM")

    Column {
        Text(text = "Available time", style = MaterialTheme.typography.titleMedium)

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            timeChoices.forEach { timeOp -> TimeOptionButton(time = timeOp) }
        }
    }
}

// кнопка для выбора времени
@Composable
fun TimeOptionButton(time: String) {
    val context = LocalContext.current
    val intent = Intent(context, TicketActivity::class.java)

    OutlinedButton(
        onClick = {
            // передача id выбранного фильма.
            intent.putExtra("time", time)

            // запуск активити.
            context.startActivity(intent)
        },
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(text = time)
    }
}

@Composable
fun MovieTitle(
    @DrawableRes image: Int = R.drawable.the_neon_demon_2016,
    title: String = "movie Title",
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
            Modifier
                .clip(shape = androidx.compose.foundation.shape.CircleShape)
                .size(64.dp)
        )
        Text(text = title, style = MaterialTheme.typography.headlineLarge)
    }
}

@Preview
@Composable
private fun PreviewTicketScreen() {
    AppTheaterTheme {
        // CinemasScreen()
    }
}
