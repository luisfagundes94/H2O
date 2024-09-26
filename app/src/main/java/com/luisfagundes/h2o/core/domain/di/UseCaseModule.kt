package com.luisfagundes.h2o.core.domain.di

import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import com.luisfagundes.h2o.core.domain.usecase.GetWaterFromToday
import com.luisfagundes.h2o.core.domain.usecase.GetWaterFromTodayImpl
import com.luisfagundes.h2o.core.domain.usecase.GetWaterHistory
import com.luisfagundes.h2o.core.domain.usecase.GetWaterHistoryImpl
import com.luisfagundes.h2o.core.domain.usecase.UpdateWater
import com.luisfagundes.h2o.core.domain.usecase.UpdateWaterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun provideGetWaterHistoryUseCase(waterRepository: WaterRepository): GetWaterHistory {
        return GetWaterHistoryImpl(waterRepository)
    }

    @Provides
    fun provideGetWaterFromToday(
        waterRepository: WaterRepository,
        userDataRepository: UserDataRepository
    ): GetWaterFromToday {
        return GetWaterFromTodayImpl(waterRepository, userDataRepository)
    }

    @Provides
    fun provideUpdateWater(waterRepository: WaterRepository): UpdateWater {
        return UpdateWaterImpl(waterRepository)
    }
}
