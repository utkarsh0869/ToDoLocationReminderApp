package com.udacity.project4.locationreminders.reminderslist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemindersListViewModelTest {

    // subjectUnderTest_actionOrInput_resultState
    // given when then

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var remindersRepositoryFake: FakeDataSource

    //subject under test
    private lateinit var remindersListViewModel: RemindersListViewModel

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        // GIVEN
        remindersRepositoryFake = FakeDataSource()
        remindersListViewModel = RemindersListViewModel(ApplicationProvider.getApplicationContext(), remindersRepositoryFake)
    }

    @After
    fun tearDownViewModel() {
        stopKoin()
    }

    @Test
    fun loadReminders_reminderDataItem_reminderListNotEmpty() = mainCoroutineRule.runBlockingTest {
        // GIVEN
        val reminder = ReminderDTO("random title", "random description", "random location",
            33.64959206484862, -117.57510323218963)

        // WHEN
        remindersRepositoryFake.saveReminder(reminder)
        remindersListViewModel.loadReminders()

        // THEN
        assertThat(remindersListViewModel.remindersList.getOrAwaitValue()).isNotEmpty()
    }

    @Test
    fun loadReminders_shouldShowErrorIfDataSourceReturnsError() {
        // GIVEN
        mainCoroutineRule.pauseDispatcher()
        remindersListViewModel.loadReminders()

        // WHEN
        remindersRepositoryFake.setReturnError(true)

        // THEN
        mainCoroutineRule.resumeDispatcher()
        assertEquals("Error getting reminders", remindersListViewModel.showSnackBar.getOrAwaitValue())
    }

    @Test
    fun loadReminders_shouldMakeShowNoDataTrueIfRemindersEmpty() = runBlockingTest {
        // GIVEN
        remindersListViewModel.loadReminders()

        // WHEN
        remindersRepositoryFake.deleteAllReminders()

        // THEN
        assertEquals(remindersListViewModel.showNoData.getOrAwaitValue(), true)
    }

    @Test
    fun showLoadingLiveData_noInput_showLoadingIsFalse() {
        // GIVEN
        mainCoroutineRule.pauseDispatcher()

        // WHEN
        remindersListViewModel.loadReminders()
        assertThat(remindersListViewModel.showLoading.getOrAwaitValue()).isTrue()
        mainCoroutineRule.resumeDispatcher()

        // THEN
        assertThat(remindersListViewModel.showLoading.getOrAwaitValue()).isFalse()
    }

}