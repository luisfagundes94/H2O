package com.luisfagundes.h2o.features.water.permission

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalInspectionMode
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus.Denied
import com.google.accompanist.permissions.rememberPermissionState

@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun PostNotificationPermission() {
    if (LocalInspectionMode.current) return
    if (VERSION.SDK_INT < VERSION_CODES.TIRAMISU) return

    val notificationPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS
    )

    LaunchedEffect(notificationPermissionState) {
        val status = notificationPermissionState.status
        if (status is Denied && !status.shouldShowRationale) {
            notificationPermissionState.launchPermissionRequest()
        }
    }
}
