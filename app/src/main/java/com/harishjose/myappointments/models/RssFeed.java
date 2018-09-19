package com.harishjose.myappointments.models;

/**
 * Created by harish.jose on 19-09-2018.
 */
public class RssFeed {
    private String guid;
    private String channelTitle;
    private String message;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
