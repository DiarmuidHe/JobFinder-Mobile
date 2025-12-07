package com.example.jobfinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobfinder.R
import com.example.jobfinder.model.NotificationEntry
import com.example.jobfinder.ui.viewmodel.NotificationDashboardViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDashboardScreen(
    viewModel: NotificationDashboardViewModel = viewModel()   // ViewModel providing notification data
) {
    // Collect notifications as state from the ViewModel
    val notifications by viewModel.notifications.collectAsState()

    Scaffold(
        topBar = {
            // Simple top app bar title
            TopAppBar(
                title = { Text("Notification History") }
            )
        }
    ) { padding ->
        if (notifications.isEmpty()) {
            // Empty state when there are no notifications
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                Text(
                    text = "No notifications yet.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            // List of stored notifications
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(notifications) { entry ->
                    NotificationRow(
                        entry = entry,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun NotificationRow(
    entry: NotificationEntry,
    modifier: Modifier = Modifier
) {
    // Memoized date formatter for timestamp display
    val dateFormat = rememberDateFormat()

    // Card for a single notification item
    Card(
        modifier = modifier.padding(dimensionResource(id = com.example.jobfinder.R.dimen.padding_small)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Notification title
                Text(
                    text = entry.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(4.dp))

                // Notification message/body
                Text(
                    text = entry.message,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(4.dp))

                // Notification timestamp formatted as date + time
                Text(
                    text = dateFormat.format(Date(entry.timestamp)),
                    style = MaterialTheme.typography.labelSmall
                )

                Divider(Modifier.padding(top = 8.dp))
            }
        }
    }
}

@Composable
private fun rememberDateFormat(): SimpleDateFormat =
    // Shared date format for all notification timestamps
    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
