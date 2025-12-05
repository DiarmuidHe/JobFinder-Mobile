package com.example.jobfinder.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job")
data class Job(
    @PrimaryKey
    val id: String,

    val title: String,
    val company: String,
    val location: String,

    val salary: String?,
    val job_type: String?,

    val description: String,
    val skills: String,

    val image: String        // URL string added
)
