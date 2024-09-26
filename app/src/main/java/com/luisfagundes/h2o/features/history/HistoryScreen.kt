package com.luisfagundes.h2o.features.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.luisfagundes.h2o.R
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.ui.theme.spacing

@Composable
fun HistoryRoute(
    viewModel: HistoryViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HistoryScreen(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState,
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryScreen(
    modifier: Modifier,
    uiState: HistoryUiState,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.history)) },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.water_history)
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = modifier.padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            when (uiState) {
                is HistoryUiState.Loading -> CircularProgressIndicator()
                is HistoryUiState.Error -> Unit
                is HistoryUiState.Success -> HistoryContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(MaterialTheme.spacing.default),
                    uiState.waterHistory
                )
            }
        }
    }

}

@Composable
private fun HistoryContent(
    modifier: Modifier,
    waterHistory: List<Water>
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(waterHistory) { water ->
            HistoryItem(
                modifier = Modifier.padding(MaterialTheme.spacing.default),
                water = water
            )
        }
    }
}

@Composable
private fun HistoryItem(
    modifier: Modifier,
    water: Water
) {
    Card {
        Row(
            modifier = modifier,
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Text(water.date.split("-").reversed().joinToString("/"))
            Spacer(modifier = Modifier.weight(1f))
            Text("${water.consumed.toInt()} / ${water.goal.toInt()} ml")
        }
    }
}