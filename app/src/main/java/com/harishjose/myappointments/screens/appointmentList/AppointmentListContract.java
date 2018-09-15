package com.harishjose.myappointments.screens.appointmentList;


import com.harishjose.myappointments.screens.common.BasePresenter;

/**
 * Created by harish.jose on 14-09-2018.
 */
class AppointmentListContract {
    public interface AppointmentListView{
        void showToast(String message);
    }

    public interface AppointmentListPresenter extends BasePresenter<AppointmentListView> {
        void fetchAppointmentList();
    }
}
