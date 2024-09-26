package com.luisfagundes.h2o.features.water

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.usecase.GetWaterFromToday
import com.luisfagundes.h2o.core.domain.usecase.UpdateWater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaterViewModel @Inject constructor(
    getWaterFromToday: GetWaterFromToday,
    private val updateWater: UpdateWater,
) : ViewModel() {
    val uiState: StateFlow<WaterUiState> = getWaterFromToday.invoke().map { data ->
        WaterUiState.Success(data)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = WaterUiState.Loading,
    )

    fun updateWaterConsumed(water: Water) = viewModelScope.launch {
        updateWater(water)
    }
}

sealed interface WaterUiState {
    data object Loading : WaterUiState
    data object Empty : WaterUiState
    data class Success(val water: Water) : WaterUiState
}
