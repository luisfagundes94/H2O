package com.luisfagundes.h2o.core.common.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val DATE_FORMAT = "yyyy-MM-dd"

fun getCurrentDate(): String {
    val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
    return dateFormat.format(Date())
}
