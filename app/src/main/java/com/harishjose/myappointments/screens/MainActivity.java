package com.harishjose.myappointments.screens;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.constants.FragmentTags;
import com.harishjose.myappointments.databinding.ActivityMainBinding;
import com.harishjose.myappointments.screens.addInvitee.AddInviteeFragment;
import com.harishjose.myappointments.screens.appointmentDetail.AppointmentDetailsFragment;
import com.harishjose.myappointments.screens.appointmentList.AppointmentListFragment;
import com.harishjose.myappointments.screens.common.BaseActivity;
import com.harishjose.myappointments.utils.FragmentNavigationManager;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        doInit();
    }

    @Override
    protected void doInit() {
        loadFragment(FragmentTags.APPOINTMENTS_LIST_FRAGMENT, null);
    }

    /**
     * Function which will load the fragment from a tag name
     *
     * @param tag
     */
    public void loadFragment(FragmentTags tag, Bundle bundle) {
        boolean isBackStackRequired = false;
        Fragment fragment = null;
        switch (tag) {
            case APPOINTMENTS_LIST_FRAGMENT:
                fragment = new AppointmentListFragment();
                break;
            case APPOINTMENT_DETAILS_FRAGMENT:
                fragment = new AppointmentDetailsFragment();
                isBackStackRequired = true;
                break;
            case ADD_INVITEE_FRAGMENT:
                fragment = new AddInviteeFragment();
                isBackStackRequired = true;
                break;

        }
        fragment.setArguments(bundle);
        FragmentNavigationManager.replaceFragment(this, fragment, getSupportFragmentManager(), R.id.content_frame,
                tag.toString(), isBackStackRequired);

    }
}
