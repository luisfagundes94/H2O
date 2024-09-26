package com.luisfagundes.h2o.core.data.mapper

import com.luisfagundes.h2o.core.data.entity.WaterEntity
import com.luisfagundes.h2o.core.domain.model.Water

object WaterMapper {
    fun WaterEntity.toDomainModel(): Water {
        return Water(
            id = id,
            date = date,
            consumed = consumed,
            goal = total,
        )
    }

    fun Water.toEntityModel(): WaterEntity {
        return WaterEntity(
            id = id,
            date = date,
            consumed = consumed,
            total = goal,
        )
    }
}
