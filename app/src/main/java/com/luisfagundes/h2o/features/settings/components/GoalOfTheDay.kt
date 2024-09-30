package com.luisfagundes.h2o.features.settings.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun GoalOfTheDay(onGoalClick: (Float) -> Unit, goal: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Goal of the day")
        Spacer(Modifier.weight(1f))
        TextButton(
            onClick = { onGoalClick(goal) }
        ) {
            Text(
                text = "${goal.toInt()} ml",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
