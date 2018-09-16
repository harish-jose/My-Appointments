package com.harishjose.myappointments.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.models.BaseModel;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.ArrayList;

/**
 * Created by harish.jose on 16-09-2018.
 */
public class AppointmentServiceImpl implements AppointmentService {

    @Override
    public void readAppointments(DataCallback<ArrayList<Appointment>, String> callback) {
        String jsonData = GeneralUtil.loadJSONFromAsset("appointmentsData.json");
        if(jsonData == null || jsonData.isEmpty()) {
            callback.onFailure(GeneralUtil.getString(R.string.error_reading_data_from_file));
            return;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        BaseModel<ArrayList<Appointment>> dataModel;
        dataModel = gson.fromJson(jsonData, new TypeToken<BaseModel<ArrayList<Appointment>>>(){}.getType());
        if(dataModel != null) {
            callback.onSuccess(dataModel.getAppointmentsList());
        } else {
            callback.onFailure(GeneralUtil.getString(R.string.error_reading_data_from_file));
        }
    }
}
