package com.harishjose.myappointments.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by harish.jose on 16-09-2018.
 */
public class BaseModel<T> {
    @SerializedName("Sessions")
    private T data;

    public T getAppointmentsList() {
        return data;
    }

    public void setAppointmentsList(T data) {
        this.data = data;
    }
}
