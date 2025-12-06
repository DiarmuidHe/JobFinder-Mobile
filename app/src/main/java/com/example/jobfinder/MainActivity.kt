package com.example.jobfinder

import android.os.Build
import androidx.compose.ui.tooling.preview.Preview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.jobfinder.ui.theme.JobFinderTheme
import com.example.jobfinder.ui.screens.JobFinderApp
import com.example.jobfinder.work.JobReminderWorker
import androidx.work.OneTimeWorkRequestBuilder

import android.widget.Button
import android.widget.Toast

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        //accept permissions for notifications
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
//                1001
//            )
//        }
//
//        //notification testing
//        val testRequest = OneTimeWorkRequestBuilder<JobReminderWorker>().build()
//        WorkManager.getInstance(this).enqueue(testRequest)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Now it’s safe to look for the button in that layout
//        val testButton: Button = findViewById(R.id.testWorkButton)
//
//        testButton.setOnClickListener {
//            val testRequest = OneTimeWorkRequestBuilder<JobReminderWorker>().build()
//            WorkManager.getInstance(this).enqueue(testRequest)
//        }

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