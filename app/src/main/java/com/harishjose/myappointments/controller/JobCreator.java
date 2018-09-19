package com.harishjose.myappointments.controller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.evernote.android.job.Job;
import com.harishjose.myappointments.services.NotificationJob;
import com.harishjose.myappointments.services.RssPollingJob;

/**
 * Created by harish.jose on 19-09-2018.
 */
public class JobCreator implements com.evernote.android.job.JobCreator {
    @Nullable
    @Override
    public Job create(@NonNull String tag) {
        if(tag.startsWith(NotificationJob.TAG)) {
            return new NotificationJob();
        } else if(tag.equals(RssPollingJob.TAG)) {
            return new RssPollingJob();
        }
        return null;
    }
}
