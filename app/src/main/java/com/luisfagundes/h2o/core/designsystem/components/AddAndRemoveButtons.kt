package com.luisfagundes.h2o.core.designsystem.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.h2o.R
import com.luisfagundes.h2o.core.designsystem.theme.spacing
import com.luisfagundes.h2o.core.domain.model.Water

@Composable
fun AddAndRemoveButtons(
    modifier: Modifier = Modifier,
    onAddWater: (water: Water) -> Unit,
    water: Water,
    extra: Float,
    onRemoveWater: (water: Water) -> Unit
) {
    Row(
        modifier = modifier
    ) {
        FloatingButton(
            onClick = { onAddWater(water.copy(consumed = water.consumed + extra)) },
            imageVector = Icons.Default.Add,
            imageDescription = stringResource(R.string.add_water_content_description)
        )
        Spacer(Modifier.width(MaterialTheme.spacing.default))
        FloatingButton(
            onClick = { onRemoveWater(water.copy(consumed = water.consumed - extra)) },
            imageVector = Icons.Default.Remove,
            imageDescription = stringResource(R.string.remove_water_content_description)
        )
    }
}
