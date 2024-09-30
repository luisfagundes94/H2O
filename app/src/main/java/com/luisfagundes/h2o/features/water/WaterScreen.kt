package com.luisfagundes.h2o.features.water

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.h2o.R
import com.luisfagundes.h2o.core.designsystem.components.AddAndRemoveButtons
import com.luisfagundes.h2o.core.designsystem.components.TopBarNavButton
import com.luisfagundes.h2o.core.designsystem.theme.waterColor
import com.luisfagundes.h2o.core.domain.model.Water
import com.neo.wave.WaveSpeed
import com.neo.wave.WaveView

@Composable
fun WaterRoute(
    viewModel: WaterViewModel = hiltViewModel(),
    onNavigateToHistory: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    WaterScreen(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState.value,
        onNavigateToHistory = onNavigateToHistory,
        onNavigateToSettings = onNavigateToSettings,
        onAddWater = viewModel::updateWaterConsumed,
        onRemoveWater = viewModel::updateWaterConsumed,
        onStartHourClick = {},
        onEndHourClick = {}
    )
}

@Composable
private fun WaterScreen(
    modifier: Modifier,
    uiState: WaterUiState,
    onAddWater: (water: Water) -> Unit,
    onRemoveWater: (water: Water) -> Unit,
    onNavigateToHistory: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onStartHourClick: () -> Unit = {},
    onEndHourClick: () -> Unit = {}
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is WaterUiState.Loading -> CircularProgressIndicator()
            is WaterUiState.Error -> Unit
            is WaterUiState.Success ->
                WaterContent(
                    modifier = Modifier.fillMaxSize(),
                    water = uiState.water,
                    onAddWater = onAddWater,
                    onRemoveWater = onRemoveWater,
                    onNavigateToHistory = onNavigateToHistory,
                    onNavigateToSettings = onNavigateToSettings,
                    onStartHourClick = onStartHourClick,
                    onEndHourClick = onEndHourClick
                )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun WaterContent(
    modifier: Modifier,
    water: Water,
    onAddWater: (water: Water) -> Unit = {},
    onRemoveWater: (water: Water) -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onStartHourClick: () -> Unit = {},
    onEndHourClick: () -> Unit = {}
) {
    val targetProgress = water.consumed / water.goal
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearEasing
        ),
        label = "waterProgress"
    )
    val waterAmount = 100f

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("${water.consumed.toInt()} / ${water.goal.toInt()} ml")
                },
                actions = {
                    Row {
                        TopBarNavButton(
                            imageVector = Icons.Default.History,
                            contentDescription = stringResource(
                                R.string.history_content_description
                            ),
                            onClick = onNavigateToHistory
                        )
                        TopBarNavButton(
                            imageVector = Icons.Default.Settings,
                            contentDescription = stringResource(
                                R.string.settings_content_description
                            ),
                            onClick = onNavigateToSettings
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            AddAndRemoveButtons(
                water = water,
                extra = waterAmount,
                onAddWater = onAddWater,
                onRemoveWater = onRemoveWater
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Box(
            modifier = modifier.padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            WaveView(
                modifier = Modifier.fillMaxSize(),
                waveColor = waterColor,
                wavePointCount = 10,
                waveSpeed = WaveSpeed.FAST,
                progress = animatedProgress,
                dragEnabled = false,
                isDebugMode = false,
                onProgressUpdated = { /* No-op */ }
            )
            Text(
                text = "${(animatedProgress * 100).toInt()}%",
                fontWeight = FontWeight.Bold,
                style = TextStyle(
                    fontSize = 60.sp,
                    shadow =
                    if (isSystemInDarkTheme()) {
                        Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    } else {
                        null
                    }
                )
            )
        }
    }
}
