package com.example.flightsearchapp.utils

import com.example.flightsearchapp.model.Airport

val emptyAirportData = Airport(
    id = 2359,
    iataCode = "",
    name = "",
    passengers = 7094
)

val fakeAirportsData = listOf(
    Airport(
        id = 9615,
        iataCode = "MUC",
        name = "Munich International Airport",
        passengers = 4046
    ),
    Airport(
        id = 8754,
        iataCode = "HAM",
        name = "Hamburg Airport",
        passengers = 4046
    ),
    Airport(
        id = 1111,
        iataCode = "AMS",
        name = "Amsterdam Airport Schiphol",
        passengers = 4046
    ),
)