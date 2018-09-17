package com.harishjose.myappointments.screens.appointmentDetail;


import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.services.AppointmentService;
import com.harishjose.myappointments.services.AppointmentServiceImpl;


/**
 * Created by harish.jose on 17-09-2018.
 */
public class AppointmentDetailsPresenter implements AppointmentDetailsContract.AppointmentDetailsPresenter {

    private AppointmentDetailsContract.AppointmentDetailsView mView;
    private AppointmentService appointmentService;

    AppointmentDetailsPresenter() {
        appointmentService = new AppointmentServiceImpl();
    }

    @Override
    public void setView(AppointmentDetailsContract.AppointmentDetailsView appointmentdetailsView) {
        this.mView = appointmentdetailsView;
    }

    @Override
    public void onStop() {
        this.mView = null;
    }

}
