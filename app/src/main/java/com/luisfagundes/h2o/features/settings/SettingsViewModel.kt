package com.luisfagundes.h2o.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<SettingsUiState>(SettingsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getUserData() = viewModelScope.launch {
        _uiState.value = SettingsUiState.Loading
        userDataRepository.userData
            .collect { userData ->
                _uiState.value = SettingsUiState.Success(userData)
            }
    }

    fun updateGoalOfTheDay(goal: Float) = viewModelScope.launch {
        userDataRepository.setGoalOfTheDay(goal)
    }

    fun updateNotificationToggle(enabled: Boolean) = viewModelScope.launch {
        userDataRepository.setNotificationEnabled(enabled)
    }
}

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(val userData: UserData) : SettingsUiState
}