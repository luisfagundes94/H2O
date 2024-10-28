package com.luisfagundes.h2o.core.data.di

import android.content.Context
import com.luisfagundes.h2o.core.data.services.alarm.WaterAlarmManagerImpl
import com.luisfagundes.h2o.core.domain.manager.WaterAlarmManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext as ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideAlarmService(@ApplicationContext context: Context): WaterAlarmManager {
        return WaterAlarmManagerImpl(context)
    }
}
