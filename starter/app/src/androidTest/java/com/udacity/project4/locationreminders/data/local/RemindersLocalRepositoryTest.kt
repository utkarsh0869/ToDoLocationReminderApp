package com.udacity.project4.locationreminders.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.dto.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Medium Test to test the repository
@MediumTest
class RemindersLocalRepositoryTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var localDataSource: RemindersLocalRepository
    private lateinit var database: RemindersDatabase

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()

        localDataSource =
            RemindersLocalRepository(database.reminderDao(), Dispatchers.Main)
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun saveReminder_retrieveReminder() = runBlocking {
        // GIVEN - A new reminder saved in the database
        val reminder = ReminderDTO("random title", "description", "local", 33.64894902087293, -117.57347244910649)
        localDataSource.saveReminder(reminder)

        // WHEN - Task retrieved by ID
        val result = localDataSource.getReminder(reminder.id) as Result.Success

        // THEN - Same reminder is returned
        assertThat(result is Result.Success, `is`(true))
        result as Result.Success

        assertThat(result.data, notNullValue())
        assertThat(result.data.id, `is`(reminder.id))
        assertThat(result.data.title, `is`(reminder.title))
        assertThat(result.data.description, `is`(reminder.description))
        assertThat(result.data.location, `is`(reminder.location))
        assertThat(result.data.latitude, `is`(reminder.latitude))
        assertThat(result.data.longitude, `is`(reminder.longitude))
    }

    @Test
    fun reminderNotFoundError() = runBlocking {
        // GIVEN - A new reminder saved in the database
        val reminder = ReminderDTO("random title", "description", "local", 33.64894902087293, -117.57347244910649)
        val result = localDataSource.getReminder(reminder.id)

        // THEN
        assertThat(result is Result.Error, `is`(true))
        result as Result.Error

        assertThat(result.message, `is`("Reminder not found!"))
    }

    @Test
    fun addingAndDeletingSingleReminder() = runBlocking {
        // GIVEN
        val reminder = ReminderDTO("random title", "description", "local", 33.64894902087293, -117.57347244910649)

        // WHEN
        localDataSource.saveReminder(reminder)
        localDataSource.deleteAllReminders()

        // THEN
        assertThat(localDataSource.getReminder(reminder.id) is Result.Error, `is`(true))
    }
}