package com.harishjose.myappointments.screens.appointmentList;


import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.services.AppointmentService;
import com.harishjose.myappointments.services.AppointmentServiceImpl;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

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
                    Collections.sort(successResponse, new Comparator<Appointment>() {
                        @Override
                        public int compare(Appointment o1, Appointment o2) {
                            return GeneralUtil.parseDateTimeToDate(o1.getActivityStartDate()).compareTo(GeneralUtil.parseDateTimeToDate(o2.getActivityStartDate()));
                        }
                    });
                    Iterator<Appointment> it = successResponse.iterator();
                    while (it.hasNext()) {
                        Appointment item = it.next();
                        if(GeneralUtil.isPastTime(GeneralUtil.parseDateTimeToDate(item.getActivityEndDate()))) {
                            it.remove();
                        }
                    }
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
