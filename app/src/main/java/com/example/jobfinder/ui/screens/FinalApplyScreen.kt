package com.example.jobfinder.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.jobfinder.model.Job

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinalApplyScreen(
    job: Job?,                   // Job being applied for (nullable)
    imageUri: Uri,               // The captured resume/photo the user is reviewing
    onCancel: () -> Unit,        // Called when user cancels the application
    onRetake: () -> Unit,        // Called when user wants to retake the photo
    onApply: () -> Unit          // Called when the user confirms the application
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            // Display dynamic title based on selected job
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = job?.title?.let { "Apply for $it" } ?: "Review Resume"
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Show company and location if job info exists
            job?.let {
                Text(
                    text = it.company,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = it.location,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Display the uploaded/captured resume image
            AsyncImage(
                model = imageUri,
                contentDescription = "Resume photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),               // Take remaining vertical space
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Action buttons: Cancel | Retake | Apply
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Cancel button
                OutlinedButton(
                    onClick = { onCancel() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                // Retake photo button
                FilledTonalButton(
                    onClick = { onRetake() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Retake")
                }

                // Apply button – also shows a toast message
                Button(
                    onClick = {
                        val jobTitle = job?.title ?: "this job"
                        Toast.makeText(
                            context,
                            "Applied for $jobTitle",
                            Toast.LENGTH_SHORT
                        ).show()

                        onApply()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Apply")
                }
            }
        }
    }
}
