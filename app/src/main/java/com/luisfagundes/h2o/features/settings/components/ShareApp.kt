package com.luisfagundes.h2o.features.settings.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.luisfagundes.h2o.R

@Composable
fun ShareApp() {
    val context = LocalContext.current
    TextButton(
        onClick = { openBrowser(context) }
    ) {
        Text(
            text = stringResource(R.string.share_app),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

private fun openBrowser(context: Context) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://github.com/luisfagundes94/H2O")
    )
    context.startActivity(intent)
}
