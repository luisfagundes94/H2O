package com.luisfagundes.h2o.core.data.di

import com.luisfagundes.h2o.core.domain.notification.WaterNotifier
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AlarmReceiverEntryPoint {
    fun getSystemTrayNotifier(): WaterNotifier
}