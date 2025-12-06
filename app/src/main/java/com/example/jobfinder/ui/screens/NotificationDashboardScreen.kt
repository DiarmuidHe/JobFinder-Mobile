package com.example.jobfinder.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobfinder.model.NotificationEntry
import com.example.jobfinder.ui.viewmodel.NotificationDashboardViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDashboardScreen(
    viewModel: NotificationDashboardViewModel = viewModel()
) {
    val notifications by viewModel.notifications.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notification History") }
            )
        }
    ) { padding ->
        if (notifications.isEmpty()) {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(notifications) { entry ->
                    NotificationRow(entry)
                }
            }
        }
    }
}

@Composable
private fun NotificationRow(entry: NotificationEntry) {
    val dateFormat = rememberDateFormat()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = entry.title, style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(4.dp))
        Text(text = entry.message, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(4.dp))
        Text(
            text = dateFormat.format(Date(entry.timestamp)),
            style = MaterialTheme.typography.labelSmall
        )
        Divider(Modifier.padding(top = 8.dp))
    }
}

@Composable
private fun rememberDateFormat(): SimpleDateFormat =
    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
