package com.harishjose.myappointments.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by harish.jose on 16-09-2018.
 */
public class InviteesBase<T> {
    @SerializedName("invitees")
    private T data;

    public T getContactsList() {
        return data;
    }

    public void setContactsList(T data) {
        this.data = data;
    }
}
