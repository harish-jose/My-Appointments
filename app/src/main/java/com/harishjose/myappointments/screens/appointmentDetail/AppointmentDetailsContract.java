package com.harishjose.myappointments.screens.appointmentDetail;


import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.screens.common.BasePresenter;

import java.util.ArrayList;

/**
 * Created by harish.jose on 14-09-2018.
 */
class AppointmentDetailsContract {
    public interface AppointmentDetailsView{
        void setAppointment(Appointment data);
        void toggleEditMode(boolean enable);
        void showToast(String message);
    }

    public interface AppointmentDetailsPresenter extends BasePresenter<AppointmentDetailsView> {
        void updateAppointmentData(Appointment appointment);

    }
}
