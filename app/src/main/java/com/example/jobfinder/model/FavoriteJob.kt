package com.example.jobfinder.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_job")
data class FavoriteJob(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val jobId: String,
    val title: String,
    val company: String,
    val location: String
)
