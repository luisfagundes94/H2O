package com.luisfagundes.h2o.core.domain.model

import androidx.compose.runtime.Stable

@Stable
data class Water(
    val id: Int,
    val date: String,
    val consumed: Float,
    val goal: Float,
) {
    companion object {
        fun empty() = Water(
            id = 0,
            date = "",
            consumed = 0f,
            goal = 0f,
        )
    }
}
