package com.luisfagundes.h2o.viewmodel

import app.cash.turbine.test
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.core.testing.MainDispatcherRule
import com.luisfagundes.h2o.features.settings.SettingsUiState
import com.luisfagundes.h2o.features.settings.SettingsViewModel
import com.luisfagundes.h2o.features.settings.mapper.toSettingsUiState
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
        coEvery { userDataRepository.userData } returns flowOf(fakeUserData)

        viewModel.getUserData()

        viewModel.uiState.test {
            assert(fakeUserData.toSettingsUiState() == awaitItem())
        }
    }

    @Test
    fun `update notification toggle calls repository`() = runTest {
        coEvery { userDataRepository.setNotificationEnabled(any()) } returns Unit

        viewModel.updateNotificationToggle(true)

        coVerify { userDataRepository.setNotificationEnabled(true) }
    }

    @Test
    fun `update goalOfTheDay calls repository`() = runTest {
        coEvery { userDataRepository.setGoalOfTheDay(any()) } returns Unit

        viewModel.updateGoalOfTheDay(2000f)

        coVerify { userDataRepository.setGoalOfTheDay(2000f) }
    }
}
