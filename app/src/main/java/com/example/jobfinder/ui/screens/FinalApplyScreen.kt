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
    job: Job?,
    imageUri: Uri,
    onCancel: () -> Unit,
    onRetake: () -> Unit,
    onApply: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
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

            // Show job info if available
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

            // Resume image preview
            AsyncImage(
                model = imageUri,
                contentDescription = "Resume photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons: Cancel | Retake | Apply
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        onCancel()
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                FilledTonalButton(
                    onClick = { onRetake() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Retake")
                }

                Button(
                    onClick = {
                        // Show applied message
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
