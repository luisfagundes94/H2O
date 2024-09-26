package com.luisfagundes.h2o.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luisfagundes.h2o.core.data.entity.WaterEntity

const val DATABASE_NAME = "h2o_database"

@Database(entities = [WaterEntity::class], version = 1)
abstract class H2oDatabase : RoomDatabase() {
    abstract fun waterDao(): WaterDao
}
