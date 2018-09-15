package com.harishjose.myappointments.screens.appointmentDetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harishjose.myappointments.screens.appointmentList.AppointmentListPresenter;
import com.harishjose.myappointments.screens.common.BaseFragment;
import com.harishjose.myappointments.utils.GeneralUtil;


/**
 * Created by harish.jose on 15-09-2018.
 */
public class AppointmentDetailsFragment extends BaseFragment{


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //binding = FragmentDeliveriesListBinding.inflate(inflater, container, false);
        init();
        return getView();
    }

    @Override
    protected void init() {
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
