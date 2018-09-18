package com.harishjose.myappointments.services;

import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.models.Appointment;

import java.util.ArrayList;

/**
 * Created by harish.jose on 16-09-2018.
 */
public interface AppointmentService {
    /**
     * Function to fetch appointments list from local json file.
     * @param callback callback on success or failure.
     */
    void readAppointments(DataCallback<ArrayList<Appointment>, String> callback);

    /**
     * Function to update appointments list to local json file.
     * @param callback callback on success or failure.
     */
    void updateAppointments(ArrayList<Appointment> appointmentsList, DataCallback<String, String> callback);
}
