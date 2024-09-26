package com.luisfagundes.h2o.core.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.window.Dialog
import com.chargemap.compose.numberpicker.NumberPicker
import com.luisfagundes.h2o.core.ui.theme.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalPicker(
    modifier: Modifier = Modifier,
    value: Int,
    unit: String,
    onValueChange: (Int) -> Unit,
    onDismissRequest: () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
        ) {
            Text(
                text = "Select your goal",
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NumberPicker(
                    range = (1000..5000 step 100),
                    value = value,
                    onValueChange = onValueChange,
                    dividersColor = MaterialTheme.colorScheme.primary,
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = MaterialTheme.typography.labelLarge.fontSize
                    )
                )
                Text(text = unit)
            }
        }
    }
}