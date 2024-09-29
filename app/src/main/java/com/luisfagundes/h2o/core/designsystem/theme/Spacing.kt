package com.luisfagundes.h2o.core.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val verySmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val default: Dp = 16.dp,
    val large: Dp = 32.dp,
    val veryLarge: Dp = 42.dp,
    val extraLarge: Dp = 52.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
