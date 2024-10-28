package com.luisfagundes.h2o.features.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
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

    init {
        getUserData()
    }

    fun getUserData() = viewModelScope.launch {
        _uiState.value = SettingsUiState.Loading
        userDataRepository.userData
            .collect { data ->
                _uiState.value = SettingsUiState.Success(
                    generalSettings = GeneralSettings(
                        goalOfTheDay = data.goalOfTheDay,
                        startHour = data.waterReminder.startHour,
                        endHour = data.waterReminder.endHour,
                        interval = data.waterReminder.interval
                    ),
                    appSettings = AppSettings(
                        darkModeEnabled = data.darkModeEnabled
                    )
                )
            }
    }

    fun updateGoalOfTheDay(goal: Float) = viewModelScope.launch {
        userDataRepository.setGoalOfTheDay(goal)
    }

    fun updateDarkMode(darkModeEnabled: Boolean) = viewModelScope.launch {
        userDataRepository.setDarkMode(darkModeEnabled)
    }
}

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Success(
        val generalSettings: GeneralSettings,
        val appSettings: AppSettings
    ) : SettingsUiState
}
