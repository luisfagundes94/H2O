package com.luisfagundes.h2o.core.data.di

import android.content.Context
import androidx.room.Room
import com.luisfagundes.h2o.core.data.database.DATABASE_NAME
import com.luisfagundes.h2o.core.data.database.H2oDatabase
import com.luisfagundes.h2o.core.data.database.WaterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideWaterDao(database: H2oDatabase): WaterDao {
        return database.waterDao()
    }

    @Provides
    fun provideH2oDatabase(@ApplicationContext context: Context): H2oDatabase {
        return Room.databaseBuilder(
            context,
            H2oDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}
