package com.example.jobfinder.data

import com.example.jobfinder.model.FavoriteRoute
import kotlinx.coroutines.flow.Flow

interface FavoriteRouteRepository {
    fun getFavoriteRoutes(): Flow<List<FavoriteRoute>>
    suspend fun insertFavoriteRoute(favoriteRoute: FavoriteRoute)
    suspend fun deleteFavoriteRoute(departureIata: String, destinationIata: String)
}

class OfflineFavoriteRouteRepository(private val favoriteRouteDao: FavoriteRouteDao) :
    FavoriteRouteRepository {

    override fun getFavoriteRoutes(): Flow<List<FavoriteRoute>> =
        favoriteRouteDao.getFavoriteRoutes()

    override suspend fun insertFavoriteRoute(favoriteRoute: FavoriteRoute) =
        favoriteRouteDao.insertFavoriteRoute(favoriteRoute)

    override suspend fun deleteFavoriteRoute(departureIata: String, destinationIata: String) =
        favoriteRouteDao.deleteFavoriteRoute(departureIata, destinationIata)


}