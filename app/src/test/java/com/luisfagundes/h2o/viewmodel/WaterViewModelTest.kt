package com.luisfagundes.h2o.viewmodel

import com.luisfagundes.h2o.core.common.result.Result
import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.usecase.GetWaterFromToday
import com.luisfagundes.h2o.core.domain.usecase.UpdateWater
import com.luisfagundes.h2o.core.testing.MainDispatcherRule
import com.luisfagundes.h2o.features.water.WaterUiState
import com.luisfagundes.h2o.features.water.WaterViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WaterViewModelTest {

    @get:Rule
    val dispatcher = MainDispatcherRule()

    private lateinit var viewModel: WaterViewModel
    private val getWaterFromToday: GetWaterFromToday = mockk()
    private val updateWater: UpdateWater = mockk()

    @Before
    fun setUp() {
        viewModel = WaterViewModel(
            getWaterFromToday,
            updateWater
        )
    }

    @Test
    fun getWater_updatesUiStateWithSuccess() = runTest {
        val water = Water.empty()
        coEvery { getWaterFromToday() } returns flowOf(water)

        viewModel.getWater()

        coVerify(exactly = 1) { getWaterFromToday() }
        assert(viewModel.uiState.value is WaterUiState.Success)
    }

    @Test
    fun updateWaterConsumed_updatesUiStateWithSuccess() = runTest {
        val water = Water.empty()
        coEvery { updateWater(water) } returns Result.Success(Unit)

        viewModel.updateWaterConsumed(water)

        coVerify(exactly = 1) { updateWater(water) }
        assert(viewModel.uiState.value is WaterUiState.Success)
    }

    @Test
    fun updateWaterConsumed_doesNotUpdateUiStateOnFailure() = runTest {
        val water = Water.empty()
        coEvery { updateWater(water) } returns Result.Error(Throwable())

        viewModel.updateWaterConsumed(water)

        coVerify(exactly = 1) { updateWater(water) }
        assert(viewModel.uiState.value is WaterUiState.Empty)
    }
}