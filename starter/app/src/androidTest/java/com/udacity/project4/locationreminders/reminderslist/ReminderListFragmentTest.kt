package com.udacity.project4.locationreminders.reminderslist

import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.core.app.ApplicationProvider
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.locationreminders.data.ReminderDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.local.LocalDB
import com.udacity.project4.locationreminders.data.local.RemindersLocalRepository
import com.udacity.project4.locationreminders.savereminder.SaveReminderViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.koin.androidx.viewmodel.dsl.viewModel

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
//UI Testing
@MediumTest
class ReminderListFragmentTest : AutoCloseKoinTest() {

    private val mockNavController = mock(NavController::class.java)
    private lateinit var remindersListViewModel: RemindersListViewModel
    private lateinit var reminderDataSource: ReminderDataSource

    @Before
    fun initializationModules() {
        stopKoin()
        val module = module {
            single {
                SaveReminderViewModel(
                    ApplicationProvider.getApplicationContext(),
                    get() as ReminderDataSource
                )
            }
            viewModel {
                RemindersListViewModel(
                    ApplicationProvider.getApplicationContext(),
                    get() as ReminderDataSource
                )
            }
            single {
                LocalDB.createRemindersDao(ApplicationProvider.getApplicationContext())
            }
            single { RemindersLocalRepository(get()) as ReminderDataSource }

        }
        startKoin {
            modules(listOf(module))
        }
        reminderDataSource = get()
        runBlocking {
            reminderDataSource.deleteAllReminders()
        }
        remindersListViewModel =
            RemindersListViewModel(ApplicationProvider.getApplicationContext(), reminderDataSource)
    }

    // Test navigation
    @Test
    fun reminderListFragment_navigateToSaveReminderOnClick() {
        // GIVEN
        val sec = launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)

        // WHEN
        sec.onFragment { Navigation.setViewNavController(it.view!!, mockNavController) }
        onView(withId(R.id.addReminderFAB)).perform(click())

        // THEN
        verify(mockNavController).navigate(ReminderListFragmentDirections.toSaveReminder())

    }

    //    Test displayed data
    @Test
    fun remindersListFragment_DisplayedInUI() {
        // GIVEN
        val reminder = ReminderDTO("title", "desc", "random location", 33.64894902087293, -117.57347244910649)

        // WHEN
        runBlocking {
            reminderDataSource.saveReminder(reminder)
        }
        launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)

        // THEN
        onView(withText(reminder.title)).check(matches(isDisplayed()))
        onView(withText(reminder.description)).check(matches(isDisplayed()))
        onView(withText(reminder.location)).check(matches(isDisplayed()))
    }
    //    Testing for error messages
    @Test
    fun remindersListFragment_DisplayErrorOnDataSourceError() {
        // GIVEN
        val reminder = ReminderDTO("Random Place", "random", "random", 33.64894902087293, -117.57347244910649)

        // WHEN
        runBlocking {
            reminderDataSource.saveReminder(reminder)
            reminderDataSource.deleteAllReminders()
        }
        launchFragmentInContainer<ReminderListFragment>(Bundle(), R.style.AppTheme)

        // THEN
        onView(withId(R.id.noDataTextView)).check(matches(isDisplayed()))
    }
}