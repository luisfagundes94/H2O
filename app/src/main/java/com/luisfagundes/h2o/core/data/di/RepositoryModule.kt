package com.luisfagundes.h2o.core.data.di

import com.luisfagundes.h2o.core.data.repository.UserDataRepositoryImpl
import com.luisfagundes.h2o.core.data.repository.WaterRepositoryImpl
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.core.domain.repository.WaterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    internal abstract fun bindUserDataRepository(
        userDataRepository: UserDataRepositoryImpl
    ): UserDataRepository

    @Binds
    internal abstract fun bindWaterRepository(waterRepository: WaterRepositoryImpl): WaterRepository
}
