package com.luisfagundes.h2o.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.features.settings.mapper.toSettingsUiState
import com.luisfagundes.h2o.features.settings.model.AppSettings
import com.luisfagundes.h2o.features.settings.model.GeneralSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
                _uiState.value = userData.toSettingsUiState()
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
    data class Success(
        val generalSettings: GeneralSettings,
        val appSettings: AppSettings
    ) : SettingsUiState
}
