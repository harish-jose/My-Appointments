package com.harishjose.myappointments.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.models.BaseModel;
import com.harishjose.myappointments.models.Contact;
import com.harishjose.myappointments.models.InviteesBase;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.ArrayList;

/**
 * Created by harish.jose on 18-09-2018.
 */
public class ContactServiceImpl implements ContactService {
    @Override
    public void readContacts(DataCallback<ArrayList<Contact>, String> callback) {
        String jsonData = GeneralUtil.loadJSONFromAsset("contactsData.json");
        if(jsonData == null || jsonData.isEmpty()) {
            callback.onFailure(GeneralUtil.getString(R.string.error_reading_contacts_from_file));
            return;
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        InviteesBase<ArrayList<Contact>> dataModel;
        dataModel = gson.fromJson(jsonData, new TypeToken<InviteesBase<ArrayList<Contact>>>(){}.getType());
        if(dataModel != null) {
            callback.onSuccess(dataModel.getContactsList());
        } else {
            callback.onFailure(GeneralUtil.getString(R.string.error_reading_contacts_from_file));
        }
    }
}
