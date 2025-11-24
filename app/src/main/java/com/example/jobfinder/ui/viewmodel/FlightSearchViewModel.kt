package com.example.jobfinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.jobfinder.FlightSearchApplication
import com.example.jobfinder.data.AirportRepository
import com.example.jobfinder.data.FavoriteRouteRepository
import com.example.jobfinder.data.UserPreferencesRepository
import com.example.jobfinder.model.Airport
import com.example.jobfinder.model.FavoriteRoute
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlightSearchViewModel(
    private val airportRepository: AirportRepository,
    private val favoriteRouteRepository: FavoriteRouteRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _flightSearchUiState = MutableStateFlow(FlightSearchUiState())
    val flightSearchUiState = _flightSearchUiState.asStateFlow()

    init {
        viewModelScope.launch {
            onSearchTextChange(userPreferencesRepository.searchString.first())
            updateFavoriteRoutes()
            if (!userPreferencesRepository.isOnboardingVisible.first()) {
                userPreferencesRepository.saveOnboardingVisibilityBooleanPreference(true)
                setOnboardingVisible()
            }
        }
    }

    fun onSearchTextChange(text: String) =
        viewModelScope.launch {
            _flightSearchUiState.update { uiState ->
                uiState.copy(
                    searchText = text,
                    isAirportSelected = false
                )
            }
            viewModelScope.launch {
                userPreferencesRepository.saveSearchStringPreference(text)
                delay(500)
                getAirportsBySearchString(text)
            }
            if (userPreferencesRepository.isOnboardingVisible.first()) {
                viewModelScope.launch {
                    _flightSearchUiState.update { uiState ->
                        uiState.copy(
                            isOnboardingVisible = false
                        )
                    }
                }
            }
        }

    fun getAirportsBySearchString(searchString: String) =
        viewModelScope.launch {
            airportRepository.getAirportsByQuery(query = searchString)
                .collect { airports ->
                    _flightSearchUiState.update { uiState ->
                        uiState.copy(
                            airports = airports
                        )
                    }
                }
        }

    fun onAirportClick(airport: Airport) {
        _flightSearchUiState.update { uiState ->
            uiState.copy(
                isAirportSelected = true,
                selectedAirport = airport,
            )
        }
    }

    fun getArrivalsForSelectedAirport(selectedAirport: Airport): Flow<List<Airport>> =
        airportRepository.getArrivalsForSelectedAirport(
            iataCode = selectedAirport.iataCode,
            name = selectedAirport.name
        )

    fun validateFavorite(route: FavoriteRoute): Boolean =
        _flightSearchUiState.value.favoriteRoutes
            .any { favoriteRoute ->
                favoriteRoute.departureIata == route.departureIata
                        && favoriteRoute.destinationIata == route.destinationIata
            }


    fun markOrUnmarkAsFavorite(route: FavoriteRoute) =
        viewModelScope.launch {
            val isFavorite = validateFavorite(route)
            if (isFavorite) {
                favoriteRouteRepository.deleteFavoriteRoute(
                    route.departureIata,
                    route.destinationIata
                )
            } else {
                favoriteRouteRepository.insertFavoriteRoute(route)
            }
            updateFavoriteRoutes()
        }

    private fun updateFavoriteRoutes() =
        viewModelScope.launch {
            _flightSearchUiState.update { uiState ->
                uiState.copy(
                    favoriteRoutes = favoriteRouteRepository.getFavoriteRoutes().first()
                )
            }
        }

    private fun setOnboardingVisible() =
        viewModelScope.launch {
            _flightSearchUiState.update { uiState ->
                uiState.copy(
                    isOnboardingVisible = true
                )
            }
        }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                val airportRepository = application.airportRepository
                val favoriteRouteRepository = application.favoriteRouteRepository
                val userPreferencesRepository = application.userPreferencesRepository
                FlightSearchViewModel(
                    airportRepository = airportRepository,
                    favoriteRouteRepository = favoriteRouteRepository,
                    userPreferencesRepository = userPreferencesRepository
                )
            }
        }
    }
}



