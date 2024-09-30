package com.luisfagundes.h2o.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luisfagundes.h2o.core.domain.model.UserData
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.core.domain.usecase.StartWaterReminder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
    private val startWaterReminder: StartWaterReminder
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> =
        userDataRepository.userData.map {
            MainActivityUiState.Success(it)
        }.stateIn(
            scope = viewModelScope,
            initialValue = MainActivityUiState.Loading,
            started = SharingStarted.WhileSubscribed(5_000)
        )

    fun setWaterReminder() = viewModelScope.launch {
        val waterReminder = WaterReminder(
            startHour = 9,
            endHour = 22,
            interval = 3
        )
        startWaterReminder(waterReminder)
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val userData: UserData) : MainActivityUiState
}
