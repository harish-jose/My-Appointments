package com.harishjose.myappointments.screens.appointmentList;


import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.screens.common.BasePresenter;

import java.util.ArrayList;

/**
 * Created by harish.jose on 14-09-2018.
 */
class AppointmentListContract {
    public interface AppointmentListView{
        void setAppointmentList(ArrayList<Appointment> dataList);
        void showToast(String message);
    }

    public interface AppointmentListPresenter extends BasePresenter<AppointmentListView> {
        void fetchAppointmentList();
    }
}
