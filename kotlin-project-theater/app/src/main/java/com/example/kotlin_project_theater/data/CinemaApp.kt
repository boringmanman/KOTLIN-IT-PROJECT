package com.example.kotlin_project_theater.data

import android.app.Application

class CinemaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.initialize(this)
    }
}
