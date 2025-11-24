package com.example.jobfinder.data

import androidx.room.Dao
import androidx.room.Query
import com.example.jobfinder.model.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {

    @Query("SELECT * FROM airport WHERE name LIKE '%' || :query || '%' " +
            "OR iata_code LIKE '%' || :query || '%' " +
            "ORDER BY passengers DESC")
    fun getAirportByQuery(query: String): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE iata_code NOT LIKE '%' || :iataCode || '%' " +
            "AND name NOT LIKE '%' || :name || '%' ORDER BY passengers DESC")
    fun getArrivalsForSelectedAirport(iataCode: String, name: String): Flow<List<Airport>>
}