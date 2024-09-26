package com.luisfagundes.h2o.features.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.usecase.GetWaterHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    getWaterHistory: GetWaterHistory
) : ViewModel() {
    val uiState: StateFlow<HistoryUiState> = getWaterHistory.invoke().map {
        HistoryUiState.Success(it ?: emptyList())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HistoryUiState.Loading
    )
}

sealed interface HistoryUiState {
    data object Loading : HistoryUiState
    data object Error : HistoryUiState
    data class Success(val waterHistory: List<Water>) : HistoryUiState
}