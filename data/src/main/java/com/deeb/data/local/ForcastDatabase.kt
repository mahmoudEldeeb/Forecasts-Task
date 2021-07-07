package com.deeb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deeb.data.local.daos.ForecastsDao
import com.deeb.data.models.entities.ForecastEntity


@Database(entities = [ForecastEntity::class], version = 1, exportSchema = false)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun forecastsDao(): ForecastsDao
}