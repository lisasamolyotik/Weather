package com.example.weather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(CityWeather::class), version = 1, exportSchema = false)
public abstract class CityWeatherDatabase : RoomDatabase(){
    abstract fun cityWeatherDao(): CityWeatherDao

    companion object {
        @Volatile
        private var INSTANCE: CityWeatherDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CityWeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityWeatherDatabase::class.java,
                    "word_database"
                ).addCallback(CityWeatherDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class CityWeatherDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.cityWeatherDao())
                }
            }
        }

        suspend fun populateDatabase(dao: CityWeatherDao) {
            dao.insertCity(CityWeather("Brest"))
            dao.insertCity(CityWeather("Vitebsk"))
            dao.insertCity(CityWeather("Gomel"))
            dao.insertCity(CityWeather("Hrodna"))
            dao.insertCity(CityWeather("Mogilev"))
            dao.insertCity(CityWeather("Minsk"))
        }
    }
}

