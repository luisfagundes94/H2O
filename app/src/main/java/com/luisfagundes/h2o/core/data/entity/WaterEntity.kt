package com.luisfagundes.h2o.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water")
data class WaterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val consumed: Float,
    val total: Float,
)
