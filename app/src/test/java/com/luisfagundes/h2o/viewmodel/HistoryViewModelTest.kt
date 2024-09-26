package com.luisfagundes.h2o.viewmodel

import com.luisfagundes.h2o.core.domain.model.Water
import com.luisfagundes.h2o.core.domain.usecase.GetWaterHistory
import com.luisfagundes.h2o.core.testing.MainDispatcherRule
import com.luisfagundes.h2o.factory.fakeWater
import com.luisfagundes.h2o.features.history.HistoryUiState
import com.luisfagundes.h2o.features.history.HistoryViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getWaterHistory: GetWaterHistory = mockk()
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setUp() {
        coEvery { getWaterHistory.invoke() } returns flowOf(null)
        viewModel = HistoryViewModel(getWaterHistory)
    }

    @Test
    fun uiState_initiallyLoading() =
        runTest {
            coEvery { getWaterHistory.invoke() } returns flowOf(emptyList())
            assert(HistoryUiState.Loading == viewModel.uiState.value)
        }

    @Test
    fun uiState_successWithData() =
        runTest {
            val waterHistory = listOf(fakeWater)
            coEvery { getWaterHistory.invoke() } returns flowOf(waterHistory)
            viewModel.uiState.collect { state ->
                if (state is HistoryUiState.Success) {
                    assertEquals(waterHistory, state.waterHistory)
                }
            }
        }

    @Test
    fun uiState_successWithEmptyList() =
        runTest {
            coEvery { getWaterHistory.invoke() } returns flowOf(null)
            viewModel.uiState.collect { state ->
                if (state is HistoryUiState.Success) {
                    assert(emptyList<Water>() == state.waterHistory)
                }
            }
        }
}
