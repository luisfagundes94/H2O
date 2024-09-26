package com.luisfagundes.h2o.core.common.extensions

import android.content.Context
import android.content.pm.PackageManager

fun Context.getAppVersion(): String {
    return try {
        val packageInfo = this.packageManager.getPackageInfo(this.packageName, 0)
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "0.0.1"
    }
}
