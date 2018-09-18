package com.harishjose.myappointments.screens.appointmentDetail;


import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.services.AppointmentService;
import com.harishjose.myappointments.services.AppointmentServiceImpl;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.ArrayList;


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
    public void updateAppointmentData(final Appointment appointment) {
        appointmentService.readAppointments(new DataCallback<ArrayList<Appointment>, String>() {
            @Override
            public void onSuccess(ArrayList<Appointment> successResponse) {
                int position;
                for (position=0; position<successResponse.size(); position++) {
                    if(successResponse.get(position).getAppointmentId() == appointment.getAppointmentId()) {
                        break;
                    }
                }
                if(position < successResponse.size()) {
                    successResponse.remove(position);
                    successResponse.add(position, appointment);
                    appointmentService.updateAppointments(successResponse, new DataCallback<String, String>() {
                        @Override
                        public void onSuccess(String successResponse) {
                            if(mView != null) {
                                mView.toggleEditMode(false);
                                mView.showToast(GeneralUtil.getString(R.string.appointment_updated_successfully));
                            }
                        }

                        @Override
                        public void onFailure(String errorResponse) {
                            if(mView != null) {
                                mView.showToast(GeneralUtil.getString(R.string.appointment_update_failed));
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(String errorResponse) {
                if(mView != null) {
                    mView.showToast(GeneralUtil.getString(R.string.appointment_update_failed));
                }
            }
        });
    }

    @Override
    public void onStop() {
        this.mView = null;
    }

}
