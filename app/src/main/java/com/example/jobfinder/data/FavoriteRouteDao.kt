package com.example.jobfinder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jobfinder.model.FavoriteRoute
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRouteDao {

    @Query("SELECT * FROM favorite")
    fun getFavoriteRoutes(): Flow<List<FavoriteRoute>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteRoute(favoriteRoute: FavoriteRoute)

    @Query("DELETE FROM favorite WHERE departure_code = :departureIata AND destination_code = :destinationIata")
    suspend fun deleteFavoriteRoute(departureIata: String, destinationIata: String)
}