package com.luisfagundes.h2o.viewmodel

import app.cash.turbine.test
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.core.testing.MainDispatcherRule
import com.luisfagundes.h2o.features.settings.SettingsUiState
import com.luisfagundes.h2o.features.settings.SettingsViewModel
import com.luisfagundes.h2o.model.fakeAppSettings
import com.luisfagundes.h2o.model.fakeGeneralSettings
import com.luisfagundes.h2o.model.fakeUserData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    @get:Rule
    val dispatcher = MainDispatcherRule()

    private lateinit var viewModel: SettingsViewModel
    private val userDataRepository: UserDataRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        viewModel = SettingsViewModel(userDataRepository)
    }

    @Test
    fun `uiState should initially emit loading`() = runTest {
        assert(viewModel.uiState.value is SettingsUiState.Loading)
    }

    @Test
    fun `uiState updates with success when userData is available`() = runTest {
        val expectedState = SettingsUiState.Success(
            generalSettings = fakeGeneralSettings,
            appSettings = fakeAppSettings
        )
        coEvery { userDataRepository.userData } returns flowOf(fakeUserData)

        viewModel.getUserData()

        viewModel.uiState.test {
            assert(expectedState == awaitItem())
        }
    }

    @Test
    fun `update dark mode toggle calls repository`() = runTest {
        coEvery { userDataRepository.setDarkMode(any()) } returns Unit

        viewModel.updateDarkMode(true)

        coVerify { userDataRepository.setDarkMode(true) }
    }

    @Test
    fun `update goalOfTheDay calls repository`() = runTest {
        coEvery { userDataRepository.setGoalOfTheDay(any()) } returns Unit

        viewModel.updateGoalOfTheDay(2000f)

        coVerify { userDataRepository.setGoalOfTheDay(2000f) }
    }
}
