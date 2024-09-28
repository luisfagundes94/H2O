package com.luisfagundes.h2o.core.data.di

import android.content.Context
import com.luisfagundes.h2o.core.data.services.alarm.AlarmService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext as ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    fun provideAlarmService(@ApplicationContext context: Context): AlarmService {
        return AlarmService(context)
    }
}
