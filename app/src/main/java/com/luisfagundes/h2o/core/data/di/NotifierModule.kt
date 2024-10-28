package com.luisfagundes.h2o.core.data.di

import android.content.Context
import com.luisfagundes.h2o.core.data.services.notification.SystemTrayNotifier
import com.luisfagundes.h2o.core.domain.notification.WaterNotifier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NotifierModule {
    @Provides
    fun provideWaterNotifier(
        @ApplicationContext context: Context
    ): WaterNotifier {
        return SystemTrayNotifier(context)
    }
}