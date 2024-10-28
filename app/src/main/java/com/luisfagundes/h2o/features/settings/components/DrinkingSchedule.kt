package com.luisfagundes.h2o.features.settings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.luisfagundes.h2o.R

@Composable
fun DrinkingSchedule(
    modifier: Modifier = Modifier,
    startHour: Int,
    endHour: Int,
    onStartHourClick: () -> Unit,
    onEndHourClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(R.string.drinking_schedule))
        Spacer(Modifier.weight(1f))
        TextButton(
            onClick = onStartHourClick
        ) { Text("$startHour:00") }
        Text(stringResource(R.string.to))
        TextButton(
            onClick = onEndHourClick
        ) { Text("$endHour:00") }
    }
}
