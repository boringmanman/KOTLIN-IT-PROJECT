package com.example.kotlin_project_theater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlin_project_theater.ui.home.HomeScreen
import com.example.kotlin_project_theater.ui.home.HomeViewModel
import com.example.kotlin_project_theater.ui.theme.AppTheaterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // val viewModel = viewModel(modelClass = HomeViewModel::class.java)
            val viewModel: HomeViewModel = viewModel(modelClass = HomeViewModel::class.java)

            AppTheaterTheme { Scaffold { HomeScreen(viewModel) } }
        }
    }
}
