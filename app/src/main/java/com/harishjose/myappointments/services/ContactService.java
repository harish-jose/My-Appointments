package com.harishjose.myappointments.services;

import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.models.Contact;

import java.util.ArrayList;

/**
 * Created by harish.jose on 17-09-2018.
 */
public interface ContactService {
    /**
     * Function to fetch contacts list from local json file.
     * @param callback callback on success or failure.
     */
    void readContacts(DataCallback<ArrayList<Contact>, String> callback);
}
