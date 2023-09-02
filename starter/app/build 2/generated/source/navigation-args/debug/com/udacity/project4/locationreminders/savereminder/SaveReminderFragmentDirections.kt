package com.udacity.project4.locationreminders.savereminder

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.udacity.project4.R

public class SaveReminderFragmentDirections private constructor() {
  public companion object {
    public fun actionSaveReminderFragmentToReminderListFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_saveReminderFragment_to_reminderListFragment)

    public fun actionSaveReminderFragmentToSelectLocationFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_saveReminderFragment_to_selectLocationFragment)
  }
}
