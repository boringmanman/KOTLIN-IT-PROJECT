package com.example.kotlin_project_theater.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlin_project_theater.ui.theme.AppTheaterTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: HomeViewModel = viewModel(modelClass = HomeViewModel::class.java)

            AppTheaterTheme { HomeScreen(viewModel) }
        }
    }
}
