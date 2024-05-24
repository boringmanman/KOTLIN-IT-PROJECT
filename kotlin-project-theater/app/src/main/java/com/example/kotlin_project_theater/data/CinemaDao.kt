package com.example.kotlin_project_theater.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemaDao {

    // Для билетов.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTicket(ticket: Ticket)

    @Update()
    suspend fun updateTicket(ticket: Ticket)

    @Delete
    suspend fun deleteTicket(ticket: Ticket)

    @Query("SELECT * FROM Ticket")
    fun getAllTickets(): Flow<List<Ticket>>

    @Query("SELECT * FROM Ticket WHERE ticketId = :ticketId")
    fun getTicketById(ticketId: Int): Flow<Ticket>

    // Для фильмов.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie: Movies)

    @Delete
    suspend fun deleteMovie(movie: Movies)

    @Query("SELECT * FROM Movies")
    fun getAllMovies(): Flow<List<Movies>>

    @Query("SELECT * FROM Movies WHERE movieId = :movieId")
    fun getMovieById(movieId: Int): Flow<Movies>
}
