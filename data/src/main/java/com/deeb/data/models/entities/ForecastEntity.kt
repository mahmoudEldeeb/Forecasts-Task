package com.deeb.data.models.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0L,
    @ColumnInfo(name = "city_name") val city: String?,
    @ColumnInfo(name = "date") val date: String?,
    @ColumnInfo(name = "weather") val weather: String?,
    @ColumnInfo(name = "temp_min") val tempMin: Double?,
    @ColumnInfo(name = "temp_max") val tempMax: Double?,
)
