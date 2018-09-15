package com.harishjose.myappointments.screens.appointmentList;


/**
 * Created by harish.jose on 14-09-2018.
 */
public class AppointmentListPresenter implements AppointmentListContract.AppointmentListPresenter {

    private AppointmentListContract.AppointmentListView mView;

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

    }
}
