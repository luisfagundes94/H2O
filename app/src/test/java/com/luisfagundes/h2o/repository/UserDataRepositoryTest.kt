package com.luisfagundes.h2o.repository

import com.luisfagundes.h2o.core.data.preferences.H2oPreferencesDataSource
import com.luisfagundes.h2o.core.data.repository.UserDataRepositoryImpl
import com.luisfagundes.h2o.core.domain.model.DarkThemeConfig
import com.luisfagundes.h2o.core.domain.repository.UserDataRepository
import com.luisfagundes.h2o.model.fakeUserData
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserDataRepositoryTest {
    private val preferencesDataSource = mockk<H2oPreferencesDataSource>()
    private lateinit var userDataRepository: UserDataRepository

    @Before
    fun setUp() {
        userDataRepository = UserDataRepositoryImpl(preferencesDataSource)
    }

    @Test
    fun userData_returnsFlowOfUserData() = runTest {
        val expectedUserData = fakeUserData
        coEvery { preferencesDataSource.userData } returns flowOf(expectedUserData)

        val result = userDataRepository.userData.first()

        assert(result == expectedUserData)
    }

    @Test
    fun setDarkModePreference_callsPreferencesDataSource() = runTest {
        val darkThemeConfig = DarkThemeConfig.DARK
        coEvery { preferencesDataSource.setDarkThemeConfig(darkThemeConfig) } returns Unit

        userDataRepository.setDarkModePreference(darkThemeConfig)

        coVerify { preferencesDataSource.setDarkThemeConfig(darkThemeConfig) }
    }

    @Test
    fun setGoalOfTheDay_callsPreferencesDataSource() = runTest {
        val goal = 2000f
        coEvery { preferencesDataSource.setGoalOfTheDay(goal) } returns Unit

        userDataRepository.setGoalOfTheDay(goal)

        coVerify { preferencesDataSource.setGoalOfTheDay(goal) }
    }

    @Test
    fun setWaterReminderInterval_callsPreferencesDataSource() = runTest {
        val hydrationReminder = fakeUserData.waterReminder.copy(interval = 2)
        coEvery { preferencesDataSource.setWaterReminder(hydrationReminder) } returns Unit

        userDataRepository.setWaterReminder(hydrationReminder)

        coVerify { preferencesDataSource.setWaterReminder(hydrationReminder) }
    }

    @Test
    fun setDynamicColorPreference_callsPreferencesDataSource() = runTest {
        val useDynamicColor = true
        coEvery { preferencesDataSource.setDynamicColorPreference(useDynamicColor) } returns Unit

        userDataRepository.setDynamicColorPreference(useDynamicColor)

        coVerify { preferencesDataSource.setDynamicColorPreference(useDynamicColor) }
    }

    @Test
    fun setNotificationEnabled_callsPreferencesDataSource() = runTest {
        val enabled = true
        coEvery { preferencesDataSource.setNotificationEnabled(enabled) } returns Unit

        userDataRepository.setNotificationEnabled(enabled)

        coVerify { preferencesDataSource.setNotificationEnabled(enabled) }
    }
}
