package com.luisfagundes.h2o.core.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.luisfagundes.h2o.core.data.entity.WaterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {
    @Query("SELECT * FROM water WHERE date = :currentDate")
    fun getWaterFrom(currentDate: String): Flow<WaterEntity?>

    @Query("SELECT * FROM water ORDER BY date DESC")
    fun getWaterHistory(): Flow<List<WaterEntity>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWater(water: WaterEntity)

    @Update
    suspend fun updateWater(water: WaterEntity)

    @Delete
    suspend fun deleteWater(water: WaterEntity)
}