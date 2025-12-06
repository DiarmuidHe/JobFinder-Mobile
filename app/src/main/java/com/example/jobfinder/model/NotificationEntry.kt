package com.example.jobfinder.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_entries")
data class NotificationEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val message: String,
    val timestamp: Long   // System.currentTimeMillis()
)