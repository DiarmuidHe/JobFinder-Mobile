package com.example.jobfinder

import androidx.compose.ui.tooling.preview.Preview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.jobfinder.ui.theme.JobFinderTheme
import com.example.jobfinder.ui.screens.JobFinderApp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JobFinderTheme {
                JobFinder()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JobFinder() {
    JobFinderApp()
}