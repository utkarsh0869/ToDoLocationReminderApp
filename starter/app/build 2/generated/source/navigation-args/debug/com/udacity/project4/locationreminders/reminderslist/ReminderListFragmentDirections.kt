package com.udacity.project4.locationreminders.reminderslist

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.udacity.project4.R

public class ReminderListFragmentDirections private constructor() {
  public companion object {
    public fun toSaveReminder(): NavDirections = ActionOnlyNavDirections(R.id.to_save_reminder)
  }
}
