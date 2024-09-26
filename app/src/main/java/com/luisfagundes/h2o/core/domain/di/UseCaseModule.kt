package com.luisfagundes.h2o.core.domain.di

import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import com.luisfagundes.h2o.core.domain.usecase.GetWaterHistory
import com.luisfagundes.h2o.core.domain.usecase.GetWaterHistoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetWaterHistoryUseCase(
        waterRepository: WaterRepository
    ): GetWaterHistory {
        return GetWaterHistoryImpl(waterRepository)
    }
}