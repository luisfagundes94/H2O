package com.luisfagundes.h2o.features.settings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.h2o.R

@Composable
fun WaterReminder(modifier: Modifier = Modifier, onIntervalClick: () -> Unit) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.water_reminder))
        Spacer(Modifier.weight(1f))
        TextButton(
            onClick = onIntervalClick
        ) {
            Text(
                text = "Every 3 hours",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
