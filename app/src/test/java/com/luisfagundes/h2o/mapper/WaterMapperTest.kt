package com.luisfagundes.h2o.mapper

import com.luisfagundes.h2o.core.data.mapper.WaterMapper.toDomainModel
import com.luisfagundes.h2o.core.data.mapper.WaterMapper.toEntityModel
import com.luisfagundes.h2o.factory.fakeWater
import com.luisfagundes.h2o.factory.fakeWaterEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class WaterMapperTest {
    @Test
    fun waterEntity_toDomainModel_mapsCorrectly() {
        val result = fakeWaterEntity.toDomainModel()

        assertEquals(fakeWater, result)
    }

    @Test
    fun water_toEntityModel_mapsCorrectly() {
        val result = fakeWater.toEntityModel()

        assertEquals(fakeWaterEntity, result)
    }
}
