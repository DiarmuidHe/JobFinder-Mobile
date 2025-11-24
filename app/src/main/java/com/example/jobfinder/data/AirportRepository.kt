package com.example.jobfinder.data

import com.example.jobfinder.model.Airport
import kotlinx.coroutines.flow.Flow

interface AirportRepository {
    fun getAirportsByQuery(query: String): Flow<List<Airport>>
    fun getArrivalsForSelectedAirport(iataCode: String, name: String): Flow<List<Airport>>
}

class OfflineAirportRepository(private val airportDao: AirportDao): AirportRepository {
    override fun getAirportsByQuery(query: String): Flow<List<Airport>> =
        airportDao.getAirportByQuery(query)

    override fun getArrivalsForSelectedAirport(iataCode: String, name: String): Flow<List<Airport>> =
        airportDao.getArrivalsForSelectedAirport(iataCode, name)
}