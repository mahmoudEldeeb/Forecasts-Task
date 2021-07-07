package com.deeb.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.deeb.data.models.entities.ForecastEntity
import io.reactivex.Single
@Dao
interface ForecastsDao {
    @Insert(onConflict = REPLACE)
    fun insertForecastData(list: List<ForecastEntity>)

    @Query("SELECT * FROM daily_forecast WHERE city_name like :city")
    fun getForecastData(city:String): Single<List<ForecastEntity>>
}

