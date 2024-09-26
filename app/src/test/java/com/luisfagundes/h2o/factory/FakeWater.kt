package com.luisfagundes.h2o.factory

import com.luisfagundes.h2o.core.data.entity.WaterEntity
import com.luisfagundes.h2o.core.domain.model.Water

val fakeWater = Water(
    id = 1,
    date = "2021-09-01",
    consumed = 1000f,
    goal = 2000f,
)

val fakeWaterEntity = WaterEntity(
    id = 1,
    date = "2021-09-01",
    consumed = 1000f,
    total = 2000f,
)

val fakeWaterEntityList = listOf(
    fakeWaterEntity,
    fakeWaterEntity.copy(id = 2, date = "2021-09-02"),
    fakeWaterEntity.copy(id = 3, date = "2021-09-03"),
)