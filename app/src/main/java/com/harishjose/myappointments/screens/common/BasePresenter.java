package com.harishjose.myappointments.screens.common;

/**
 * Created by harish.jose on 02-09-2018.
 */

public interface BasePresenter<T> {
    void setView(T t);
    void onStop();
}
