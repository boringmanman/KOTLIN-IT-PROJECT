package com.example.kotlin_project_theater.data

import kotlinx.coroutines.flow.Flow

class Repository(private val cinemaDao: CinemaDao) {

    // Для билетов.
    fun getTickets(): Flow<List<Ticket>> = cinemaDao.getAllTickets()

    fun getTicketById(ticketId: Int): Flow<Ticket?> = cinemaDao.getTicketById(ticketId)

    suspend fun insertTicket(ticket: Ticket) = cinemaDao.insertTicket(ticket)

    suspend fun updateTicket(ticket: Ticket) = cinemaDao.updateTicket(ticket)

    suspend fun deleteTicket(ticket: Ticket) = cinemaDao.deleteTicket(ticket)

    // Для фильмов.
    fun getMovies(): Flow<List<Movies>> = cinemaDao.getAllMovies()

    fun getMovieById(movieId: Int): Flow<Movies> = cinemaDao.getMovieById(movieId)

    suspend fun insertMovie(movie: Movies) = cinemaDao.insertMovie(movie)

    suspend fun deleteMovie(movie: Movies) = cinemaDao.deleteMovie(movie)
}
