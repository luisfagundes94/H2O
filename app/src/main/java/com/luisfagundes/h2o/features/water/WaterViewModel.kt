package com.luisfagundes.h2o.features.water

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.h2o.core.common.result.Result
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.usecase.GetWaterFromToday
import com.luisfagundes.h2o.core.domain.usecase.UpdateWater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WaterViewModel @Inject constructor(
    private val getWaterFromToday: GetWaterFromToday,
    private val updateWater: UpdateWater
) : ViewModel() {

    private val _uiState = MutableStateFlow<WaterUiState>(WaterUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getWater() = viewModelScope.launch {
        getWaterFromToday().collect { water ->
            _uiState.update { WaterUiState.Success(water) }
        }
    }

    fun updateWaterConsumed(water: Water) = viewModelScope.launch {
        val result = updateWater(water)
        if (result is Result.Success) {
            _uiState.update { WaterUiState.Success(water) }
        } else {
            _uiState.update { WaterUiState.Empty }
        }
    }
}

sealed interface WaterUiState {
    data object Loading : WaterUiState
    data object Empty : WaterUiState
    data class Success(val water: Water) : WaterUiState
}