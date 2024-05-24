package com.example.kotlin_project_theater.data

import android.content.Context

object Graph {

    lateinit var database: CinemaDatabase
        private set

    val repository by lazy { Repository(cinemaDao = database.cinemaDao()) }

    fun initialize(context: Context) {
        database = CinemaDatabase.getInstance(context)
    }
}
