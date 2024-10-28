package com.luisfagundes.h2o.viewmodel

import app.cash.turbine.test
import com.luisfagundes.h2o.core.common.utils.AppConstants.DEFAULT_END_HOUR
import com.luisfagundes.h2o.core.common.utils.AppConstants.DEFAULT_INTERVAL
import com.luisfagundes.h2o.core.common.utils.AppConstants.DEFAULT_START_HOUR
import com.luisfagundes.h2o.core.domain.model.WaterReminder
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.core.domain.usecase.UpdateWaterReminder
import com.luisfagundes.h2o.core.testing.MainDispatcherRule
import com.luisfagundes.h2o.main.MainUiState
import com.luisfagundes.h2o.main.MainViewModel
import com.luisfagundes.h2o.model.fakeUserData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userDataRepository = mockk<UserDataRepository>(relaxed = true)
    private val updateWaterReminder = mockk<UpdateWaterReminder>(relaxed = true)
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(
            userDataRepository = userDataRepository,
            updateWaterReminder = updateWaterReminder
        )
    }

    @Test
    fun `uiState should initially emit Loading`() = runTest {
        viewModel.uiState.test {
            assert(MainUiState.Loading == awaitItem())
        }
    }

    @Test
    fun `uiState should emit success when userData is available`() = runTest {
        coEvery { userDataRepository.userData } returns flowOf(fakeUserData)

        viewModel.getUserData()

        viewModel.uiState.test {
            assert(MainUiState.Success(fakeUserData) == awaitItem())
        }
    }

    @Test
    fun `updateWaterSchedule should call updateWaterReminder with provided waterReminder`() =
        runTest {
            val waterReminder = WaterReminder(startHour = 9, endHour = 18, interval = 3)

            viewModel.updateWaterSchedule(waterReminder)

            coVerify { updateWaterReminder(waterReminder) }
        }

    @Test
    fun `setDefaultWaterReminder should call updateWaterReminder with default values`() = runTest {
        coEvery { userDataRepository.userData } returns flowOf(
            fakeUserData.copy(appLaunchedBefore = false)
        )

        viewModel.setDefaultWaterReminder()

        val defaultReminder = WaterReminder(
            startHour = DEFAULT_START_HOUR,
            endHour = DEFAULT_END_HOUR,
            interval = DEFAULT_INTERVAL
        )

        coVerify { updateWaterReminder(defaultReminder) }
        coVerify { userDataRepository.setAppLaunchedBefore() }
    }

    @Test
    fun `setDefaultWaterReminder should not call updateWaterReminder if app launched before`() =
        runTest {
            coEvery { userDataRepository.userData } returns flowOf(fakeUserData)

            viewModel.setDefaultWaterReminder()

            coVerify(exactly = 0) { updateWaterReminder(any()) }
        }
}
