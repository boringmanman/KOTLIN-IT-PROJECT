package com.example.kotlin_project_theater.ui.purchase

import androidx.annotation.DrawableRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin_project_theater.data.Cinema
import com.example.kotlin_project_theater.data.Graph
import com.example.kotlin_project_theater.data.Movies
import com.example.kotlin_project_theater.data.Repository
import com.example.kotlin_project_theater.data.TableData
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CinemasViewModel(private val repository: Repository = Graph.repository) : ViewModel() {

    var state by mutableStateOf(PurchaseState())
        private set

    fun setMovieById(id: Int) {
        viewModelScope.launch {
            repository.getMovieById(id).collectLatest { state = state.copy(movie = it) }
        }
    }

    fun setTime(time: String) {
        viewModelScope.launch { state = state.copy(time = time) }
    }

    fun setDate(date: String) {
        viewModelScope.launch { state = state.copy(date = date) }
    }

    fun addTicket() {
        viewModelScope.launch {

            // repository.insertTicket(ticket = Ticket())
        }
    }
}

data class PurchaseState(
    val cinemas: List<Cinema> = emptyList(),
    val time: String = "", // время сеанса.
    val date: String = "",
    val movieId: Int = 0,
    val movie: Movies = TableData.moviesList[0],
    val movieDetails: MovieDetails = MovieDetails()
)

data class MovieDetails(
    val id: Int = 0,
    val title: String = "",
    val year: Int = 0,
    val length: Int = 0,
    val description: String = "",
    @DrawableRes val poster: Int = 0,
    val price: Float = 0.0f
)

fun MovieDetails.toMovie(): Movies =
    Movies(
        movieId = id,
        title = title,
        year = year,
        length = length,
        description = description,
        poster = poster,
        price = price
    )

/*
fun MovieDetails.toMovieState(): PurchaseState = PurchaseState(
    cinemas = this,
    title = title,
    year = year,
    length = length,
    description = description,
    poster = poster
)*/
