package com.example.jobfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.jobfinder.ui.JobFinderApp
import com.example.jobfinder.ui.theme.JobFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobFinderTheme {
                JobFinderApp()
            }
        }
    }
}