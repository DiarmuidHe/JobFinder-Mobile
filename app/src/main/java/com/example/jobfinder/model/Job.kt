package com.example.jobfinder.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job")
data class Job(
    @PrimaryKey
    val id: String,
    val title: String,
    val company: String,
    val location: String
)