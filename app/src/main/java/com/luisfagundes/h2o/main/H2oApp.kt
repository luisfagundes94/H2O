package com.luisfagundes.h2o.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.luisfagundes.h2o.core.designsystem.components.H2oBackground
import com.luisfagundes.h2o.navigation.H2oNavHost

@Composable
fun H2oApp(modifier: Modifier = Modifier) {
    H2oBackground(modifier = modifier) {
        H2oNavHost()
    }
}
