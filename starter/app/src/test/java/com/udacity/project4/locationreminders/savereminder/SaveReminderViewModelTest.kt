package com.udacity.project4.locationreminders.savereminder

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.R
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.getOrAwaitValue
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {

    // we need the instant task executor rule for live data testing
    // JUnit rules allow us to run some code before and after each test runs
    // the following rule runs all architectural related background jobs in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var remindersRepositoryFake: FakeDataSource

    private lateinit var saveReminderViewModel: SaveReminderViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        // GIVEN
        remindersRepositoryFake = FakeDataSource()
        saveReminderViewModel = SaveReminderViewModel(ApplicationProvider.getApplicationContext(), remindersRepositoryFake)
    }

    @Test
    fun validateEnteredData_emptyTitle_snackBarUpdated() {
        // WHEN
        val reminder = ReminderDataItem("", "random description", "random location",
            33.64959206484862, -117.57510323218963)

        // THEN
        assertEquals(saveReminderViewModel.validateEnteredData(reminder), false)
        assertThat(saveReminderViewModel.showSnackBarInt.getOrAwaitValue()).isEqualTo(R.string.err_enter_title)
    }

    @Test
    fun validateEnteredData_emptyLocation_snackBarUpdated() {
        // WHEN
        val reminder = ReminderDataItem("random title", "random description", "",
            33.64959206484862, -117.57510323218963)

        // THEN
        assertEquals(saveReminderViewModel.validateEnteredData(reminder), false)
        assertThat(saveReminderViewModel.showSnackBarInt.getOrAwaitValue()).isEqualTo(R.string.err_select_location)
    }

    @Test
    fun saveReminder_reminderDataItem_successLiveDataState() {
        // WHEN
        val reminder = ReminderDataItem("random", "random", "random",
            33.64959206484862, -117.57510323218963)
        mainCoroutineRule.pauseDispatcher()
        saveReminderViewModel.saveReminder(reminder)
        mainCoroutineRule.resumeDispatcher()

        // THEN
        val expectedString =
            saveReminderViewModel.getApplication<Application>().resources.getString(R.string.reminder_saved)

        assertEquals(saveReminderViewModel.showLoading.getOrAwaitValue(), false)
        assertThat(saveReminderViewModel.showToast.getOrAwaitValue()).isEqualTo(expectedString)
        assertEquals(saveReminderViewModel.navigationCommand.getOrAwaitValue(), NavigationCommand.Back)
    }

    @After
    fun tearDownViewModel() {
        stopKoin()
    }
}