package com.luisfagundes.h2o.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Android
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.h2o.R
import com.luisfagundes.h2o.core.common.extensions.getAppVersion
import com.luisfagundes.h2o.core.designsystem.components.GoalPicker
import com.luisfagundes.h2o.core.designsystem.theme.spacing
import com.luisfagundes.h2o.features.settings.components.DrinkingSchedule
import com.luisfagundes.h2o.features.settings.components.GoalOfTheDay
import com.luisfagundes.h2o.features.settings.components.WaterReminder
import com.luisfagundes.h2o.features.settings.model.AppSettings
import com.luisfagundes.h2o.features.settings.model.GeneralSettings

@Composable
fun SettingsRoute(viewModel: SettingsViewModel = hiltViewModel(), onBackPressed: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.default),
        uiState = uiState,
        onBackPressed = onBackPressed,
        onChangeGoalOfTheDay = viewModel::updateGoalOfTheDay,
        onChangeNotificationToggle = viewModel::updateNotificationToggle,
        onChangeStartHour = { },
        onChangeEndHour = { },
        onChangeInterval = { }
    )

    LaunchedEffect(Unit) {
        viewModel.getUserData()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen(
    modifier: Modifier,
    uiState: SettingsUiState,
    onBackPressed: () -> Unit,
    onChangeGoalOfTheDay: (Float) -> Unit,
    onChangeNotificationToggle: (Boolean) -> Unit,
    onChangeStartHour: () -> Unit,
    onChangeEndHour: () -> Unit,
    onChangeInterval: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = modifier.padding(contentPadding)
        ) {
            when (uiState) {
                is SettingsUiState.Loading -> Unit
                is SettingsUiState.Success ->
                    SettingsContent(
                        modifier = Modifier,
                        generalSettings = uiState.generalSettings,
                        appSettings = uiState.appSettings,
                        onChangeNotificationToggle = onChangeNotificationToggle,
                        onChangeGoalOfTheDay = onChangeGoalOfTheDay,
                        onChangeStartHour = onChangeStartHour,
                        onChangeEndHour = onChangeEndHour,
                        onChangeInterval = onChangeInterval
                    )
            }
        }
    }
}

@Composable
private fun SettingsContent(
    modifier: Modifier,
    generalSettings: GeneralSettings,
    appSettings: AppSettings,
    onChangeGoalOfTheDay: (Float) -> Unit,
    onChangeNotificationToggle: (Boolean) -> Unit,
    onChangeStartHour: () -> Unit,
    onChangeEndHour: () -> Unit,
    onChangeInterval: () -> Unit
) {
    var showGoalPicker by remember { mutableStateOf(false) }

    if (showGoalPicker) {
        GoalPicker(
            modifier = Modifier
                .clip(RoundedCornerShape(MaterialTheme.spacing.default))
                .background(MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.spacing.large),
            value = generalSettings.goalOfTheDay.toInt(),
            unit = stringResource(R.string.ml),
            onValueChange = { onChangeGoalOfTheDay(it.toFloat()) },
            onDismissRequest = { showGoalPicker = false }
        )
    }

    Column(
        modifier = modifier
    ) {
        AboutSection()
        HorizontalDivider(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.default)
        )
        GeneralSection(
            generalSettings = generalSettings,
            onGoalOfTheDayClick = { showGoalPicker = true },
            onStartHourClick = onChangeStartHour,
            onEndHourClick = onChangeEndHour,
            onIntervalClick = onChangeInterval
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.default)
        )
        AppSettingsSection(
            appSettings = appSettings,
            onNotificationCheckedChange = onChangeNotificationToggle
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.default)
        )
        Text(
            modifier = Modifier.clickable { },
            text = stringResource(R.string.share_app),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun AboutSection() {
    val context = LocalContext.current
    val versionName = "v${context.getAppVersion()}"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = Icons.Default.Android,
            contentDescription = stringResource(R.string.app_icon)
        )
        Spacer(Modifier.width(MaterialTheme.spacing.default))
        Column {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = versionName
            )
        }
    }
}

@Composable
private fun GeneralSection(
    modifier: Modifier = Modifier,
    generalSettings: GeneralSettings,
    onGoalOfTheDayClick: (Float) -> Unit,
    onStartHourClick: () -> Unit,
    onEndHourClick: () -> Unit,
    onIntervalClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
    ) {
        Text(
            text = stringResource(R.string.general_section_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        GoalOfTheDay(
            goal = generalSettings.goalOfTheDay,
            onGoalClick = onGoalOfTheDayClick
        )
        DrinkingSchedule(
            onStartHourClick = onStartHourClick,
            onEndHourClick = onEndHourClick
        )
        WaterReminder(
            onIntervalClick = onIntervalClick
        )
    }
}

@Composable
private fun AppSettingsSection(
    modifier: Modifier = Modifier,
    appSettings: AppSettings,
    onNotificationCheckedChange: (Boolean) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.default)
    ) {
        Text(
            text = stringResource(R.string.app_settings),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.notification))
            Spacer(Modifier.weight(1f))
            Switch(
                checked = appSettings.notificationEnabled,
                onCheckedChange = onNotificationCheckedChange
            )
        }
    }
}
