package com.example.jobfinder.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteRoute(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "departure_code")
    val departureIata: String,

    @ColumnInfo(name = "destination_code")
    val destinationIata: String
)