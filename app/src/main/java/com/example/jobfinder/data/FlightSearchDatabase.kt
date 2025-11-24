package com.example.jobfinder.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jobfinder.model.Airport
import com.example.jobfinder.model.FavoriteRoute

@Database(entities = [Airport::class, FavoriteRoute::class], version = 1, exportSchema = false)
abstract class FlightSearchDatabase: RoomDatabase() {

    abstract fun airportDao(): AirportDao
    abstract fun favoriteRoutesDao(): FavoriteRouteDao

    companion object {
        @Volatile
        private var Instance: FlightSearchDatabase? = null

        fun getDatabase(context: Context): FlightSearchDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, FlightSearchDatabase::class.java, "flight_search")
                    .createFromAsset("database/flight_search.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
