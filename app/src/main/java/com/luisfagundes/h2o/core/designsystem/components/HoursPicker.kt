package com.luisfagundes.h2o.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.chargemap.compose.numberpicker.Hours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.luisfagundes.h2o.R
import com.luisfagundes.h2o.core.designsystem.theme.spacing

@Composable
fun HoursPicker(
    modifier: Modifier = Modifier,
    hours: Hours,
    onSelectHours: (Hours) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            Text(
                text = stringResource(R.string.select_hour),
                style = MaterialTheme.typography.titleMedium
            )
            HoursNumberPicker(
                hoursRange = (0..23),
                value = hours,
                onValueChange = onSelectHours
            )
        }
    }
}
