package com.luisfagundes.h2o.core.designsystem.components

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    imageDescription: String,
    onClick: () -> Unit = {},
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageDescription
        )
    }
}