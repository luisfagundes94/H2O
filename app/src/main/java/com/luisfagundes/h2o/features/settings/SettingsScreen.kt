package com.luisfagundes.h2o.features.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.h2o.R
import com.luisfagundes.h2o.core.common.extensions.getAppVersion
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.ui.theme.spacing

@Composable
fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SettingsScreen(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.default),
        uiState = uiState,
        onBackPressed = onBackPressed,
        onGoalOfTheDayClick = {},
        onNotificationCheckedChange = viewModel::updateNotificationToggle,
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
    onGoalOfTheDayClick: (Float) -> Unit,
    onNotificationCheckedChange: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings)) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                        )
                    }
                },
            )
        },
    ) { contentPadding ->
        Box(
            modifier = modifier.padding(contentPadding),
        ) {
            when (uiState) {
                is SettingsUiState.Loading -> Unit
                is SettingsUiState.Success ->
                    SettingsContent(
                        modifier = Modifier,
                        userData = uiState.userData,
                        onGoalOfTheDayClick = onGoalOfTheDayClick,
                        notificationChecked = uiState.userData.notificationEnabled,
                        onNotificationCheckedChange = onNotificationCheckedChange,
                    )
            }
        }
    }
}

@Composable
private fun SettingsContent(
    modifier: Modifier,
    userData: UserData,
    onGoalOfTheDayClick: (Float) -> Unit,
    notificationChecked: Boolean,
    onNotificationCheckedChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        AppSection()
        HorizontalDivider(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.default),
        )
        GeneralSection(
            goalOfTheDay = userData.waterGoal,
            onGoalOfTheDayClick = onGoalOfTheDayClick,
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.default),
        )
        AppSettingsSection(
            notificationChecked = notificationChecked,
            onNotificationCheckedChange = onNotificationCheckedChange,
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = MaterialTheme.spacing.default),
        )
        Text(
            modifier = Modifier.clickable { },
            text = stringResource(R.string.share_app),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun AppSection() {
    val context = LocalContext.current
    val versionName = "v${context.getAppVersion()}"

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(50.dp),
            imageVector = Icons.Default.Android,
            contentDescription = stringResource(R.string.app_icon),
        )
        Spacer(Modifier.width(MaterialTheme.spacing.default))
        Column {
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = versionName,
            )
        }
    }
}

@Composable
private fun GeneralSection(
    modifier: Modifier = Modifier,
    goalOfTheDay: Float,
    onGoalOfTheDayClick: (Float) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.general_section_title),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(MaterialTheme.spacing.default))
        Row {
            Text("Goal of the day")
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.clickable { onGoalOfTheDayClick(goalOfTheDay) },
                text = "${goalOfTheDay.toInt()} ml",
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Composable
private fun AppSettingsSection(
    modifier: Modifier = Modifier,
    notificationChecked: Boolean,
    onNotificationCheckedChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.app_settings),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
        Spacer(Modifier.height(MaterialTheme.spacing.default))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(stringResource(R.string.notification))
            Spacer(Modifier.weight(1f))
            Switch(
                checked = notificationChecked,
                onCheckedChange = onNotificationCheckedChange,
            )
        }
        Spacer(Modifier.height(MaterialTheme.spacing.default))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(stringResource(R.string.time_reminder))
            Spacer(Modifier.weight(1f))
            Text(
                modifier = Modifier.clickable { },
                text = "Every 3 hours",
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
