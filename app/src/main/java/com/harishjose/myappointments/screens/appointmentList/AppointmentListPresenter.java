package com.harishjose.myappointments.screens.appointmentList;


import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.services.AppointmentService;
import com.harishjose.myappointments.services.AppointmentServiceImpl;

import java.util.ArrayList;

/**
 * Created by harish.jose on 14-09-2018.
 */
public class AppointmentListPresenter implements AppointmentListContract.AppointmentListPresenter {

    private AppointmentListContract.AppointmentListView mView;
    private AppointmentService appointmentService;

    AppointmentListPresenter() {
        appointmentService = new AppointmentServiceImpl();
    }

    @Override
    public void setView(AppointmentListContract.AppointmentListView appointmentListView) {
        this.mView = appointmentListView;
    }

    @Override
    public void onStop() {
        this.mView = null;
    }

    @Override
    public void fetchAppointmentList() {
        appointmentService.readAppointments(new DataCallback<ArrayList<Appointment>, String>() {
            @Override
            public void onSuccess(ArrayList<Appointment> successResponse) {
                if(mView != null) {
                    mView.setAppointmentList(successResponse);
                }
            }

            @Override
            public void onFailure(String errorResponse) {
                if(mView != null) {
                    mView.showToast(errorResponse);
                }
            }
        });

    }
}
