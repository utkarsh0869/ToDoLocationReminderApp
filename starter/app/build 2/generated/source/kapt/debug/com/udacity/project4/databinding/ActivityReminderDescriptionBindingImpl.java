package com.udacity.project4.databinding;
import com.udacity.project4.R;
import com.udacity.project4.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityReminderDescriptionBindingImpl extends ActivityReminderDescriptionBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = null;
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityReminderDescriptionBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds));
    }
    private ActivityReminderDescriptionBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.widget.TextView) bindings[1]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.reminderCoordinatesTextView.setTag(null);
        this.reminderDescriptionTextView.setTag(null);
        this.reminderLocationTextView.setTag(null);
        this.reminderTitleTextView.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.reminderDataItem == variableId) {
            setReminderDataItem((com.udacity.project4.locationreminders.reminderslist.ReminderDataItem) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setReminderDataItem(@Nullable com.udacity.project4.locationreminders.reminderslist.ReminderDataItem ReminderDataItem) {
        this.mReminderDataItem = ReminderDataItem;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.reminderDataItem);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String reminderDataItemLocation = null;
        com.udacity.project4.locationreminders.reminderslist.ReminderDataItem reminderDataItem = mReminderDataItem;
        java.lang.String reminderDataItemTitle = null;
        java.lang.Double reminderDataItemLatitude = null;
        java.lang.String reminderDataItemDescription = null;
        java.lang.Double reminderDataItemLongitude = null;
        java.lang.String reminderCoordinatesTextViewAndroidStringLatLongSnippetReminderDataItemLatitudeReminderDataItemLongitude = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (reminderDataItem != null) {
                    // read reminderDataItem.location
                    reminderDataItemLocation = reminderDataItem.getLocation();
                    // read reminderDataItem.title
                    reminderDataItemTitle = reminderDataItem.getTitle();
                    // read reminderDataItem.latitude
                    reminderDataItemLatitude = reminderDataItem.getLatitude();
                    // read reminderDataItem.description
                    reminderDataItemDescription = reminderDataItem.getDescription();
                    // read reminderDataItem.longitude
                    reminderDataItemLongitude = reminderDataItem.getLongitude();
                }


                // read @android:string/lat_long_snippet
                reminderCoordinatesTextViewAndroidStringLatLongSnippetReminderDataItemLatitudeReminderDataItemLongitude = reminderCoordinatesTextView.getResources().getString(R.string.lat_long_snippet, reminderDataItemLatitude, reminderDataItemLongitude);
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.reminderCoordinatesTextView, reminderCoordinatesTextViewAndroidStringLatLongSnippetReminderDataItemLatitudeReminderDataItemLongitude);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.reminderDescriptionTextView, reminderDataItemDescription);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.reminderLocationTextView, reminderDataItemLocation);
            androidx.databinding.adapters.TextViewBindingAdapter.setText(this.reminderTitleTextView, reminderDataItemTitle);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): reminderDataItem
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}