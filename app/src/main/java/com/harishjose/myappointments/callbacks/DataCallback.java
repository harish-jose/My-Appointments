package com.harishjose.myappointments.callbacks;

/**
 * Created by harish.jose on 14-09-2018.
 */
public interface DataCallback<T1, T2> {
    void onSuccess(T1 successResponse);
    void onFailure(T2 errorResponse);
}
