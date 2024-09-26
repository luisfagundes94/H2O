package com.luisfagundes.h2o.viewmodel

import com.luisfagundes.h2o.core.common.result.Result
import com.luisfagundes.h2o.core.domain.usecase.UpdateWater
import com.luisfagundes.h2o.core.testing.MainDispatcherRule
import com.luisfagundes.h2o.core.testing.usecase.FakeGetWaterFromToday
import com.luisfagundes.h2o.features.water.WaterUiState
import com.luisfagundes.h2o.features.water.WaterViewModel
import com.luisfagundes.h2o.model.fakeUserData
import com.luisfagundes.h2o.model.fakeWater
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WaterViewModelTest {
    @get:Rule
    val dispatcher = MainDispatcherRule()

    private lateinit var viewModel: WaterViewModel
    private val getWaterFromToday = FakeGetWaterFromToday()
    private val updateWater: UpdateWater = mockk()

    @Before
    fun setUp() {
        viewModel = WaterViewModel(
            getWaterFromToday,
            updateWater
        )
    }

    @Test
    fun getWater_initiallyLoading() = runTest {
        assertEquals(WaterUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun getWater_updatesUiStateWithSuccess() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        getWaterFromToday.emitWater(fakeWater, fakeUserData)

        val state = viewModel.uiState.value
        assertEquals(WaterUiState.Success(fakeWater), state)
    }

    @Test
    fun updateWaterConsumed_updatesUiStateWithSuccess() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        coEvery { updateWater(fakeWater) } returns Result.Success(Unit)

        viewModel.updateWaterConsumed(fakeWater)

        coVerify(exactly = 1) { updateWater(fakeWater) }
        assertEquals(WaterUiState.Success(fakeWater), viewModel.uiState.value)
    }

    @Test
    fun updateWaterConsumed_doesNotUpdateUiStateOnFailure() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        coEvery { updateWater(fakeWater) } returns Result.Error(Throwable())

        viewModel.updateWaterConsumed(fakeWater)

        coVerify(exactly = 1) { updateWater(fakeWater) }
        assertEquals(WaterUiState.Error, viewModel.uiState.value)
    }
}
