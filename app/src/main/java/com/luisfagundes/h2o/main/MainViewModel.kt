package com.luisfagundes.h2o.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.h2o.core.common.utils.AppConstants.DEFAULT_END_HOUR
import com.luisfagundes.h2o.core.common.utils.AppConstants.DEFAULT_INTERVAL
import com.luisfagundes.h2o.core.common.utils.AppConstants.DEFAULT_START_HOUR
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.core.domain.usecase.UpdateWaterReminder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val updateWaterReminder: UpdateWaterReminder
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        getUserData()
    }

    fun getUserData() = viewModelScope.launch {
        _uiState.value = MainUiState.Loading
        userDataRepository.userData.collect {
            _uiState.value = MainUiState.Success(it)
        }
    }

    fun updateWaterSchedule(waterReminder: WaterReminder) = viewModelScope.launch {
        updateWaterReminder(waterReminder)
    }

    fun setDefaultWaterReminder() = viewModelScope.launch {
        val waterReminder = WaterReminder(
            startHour = DEFAULT_START_HOUR,
            endHour = DEFAULT_END_HOUR,
            interval = DEFAULT_INTERVAL
        )
        updateWaterReminder(waterReminder)
    }
}

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Success(val userData: UserData) : MainUiState
}
