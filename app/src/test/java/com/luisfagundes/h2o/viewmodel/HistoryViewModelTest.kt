package com.luisfagundes.h2o.viewmodel

import com.luisfagundes.h2o.core.testing.MainDispatcherRule
import com.luisfagundes.h2o.core.testing.usecase.FakeGetWaterHistory
import com.luisfagundes.h2o.features.history.HistoryUiState
import com.luisfagundes.h2o.features.history.HistoryViewModel
import com.luisfagundes.h2o.model.fakeWaterHistory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getWaterHistory = FakeGetWaterHistory()
    private lateinit var viewModel: HistoryViewModel

    @Before
    fun setUp() {
        viewModel = HistoryViewModel(getWaterHistory)
    }

    @Test
    fun uiState_initiallyLoading() = runTest {
        assertEquals(HistoryUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun uiState_successWithData() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        getWaterHistory.emitWaterHistory(fakeWaterHistory)

        val state = viewModel.uiState.value
        assertEquals(HistoryUiState.Success(fakeWaterHistory), state)
    }

    @Test
    fun uiState_successWithEmptyList() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }

        getWaterHistory.emitWaterHistory(emptyList())

        val state = viewModel.uiState.value
        assertEquals(HistoryUiState.Success(emptyList()), state)
    }
}
