package com.harishjose.myappointments.screens.addInvitee;


import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.models.Contact;
import com.harishjose.myappointments.screens.common.BasePresenter;

import java.util.ArrayList;

/**
 * Created by harish.jose on 17-09-2018.
 */
class AddInviteeContract {
    public interface AddInviteeView{
        void setContactList(ArrayList<Contact> dataList);
        void showToast(String message);
    }

    public interface AddInviteePresenter extends BasePresenter<AddInviteeView> {
        void fetchContactList(Appointment appointment);
    }
}
