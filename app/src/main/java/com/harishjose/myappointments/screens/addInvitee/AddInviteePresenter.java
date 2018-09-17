package com.harishjose.myappointments.screens.addInvitee;

import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.models.Contact;
import com.harishjose.myappointments.services.ContactService;
import com.harishjose.myappointments.services.ContactServiceImpl;

import java.util.ArrayList;


/**
 * Created by harish.jose on 17-09-2018.
 */
public class AddInviteePresenter implements AddInviteeContract.AddInviteePresenter {

    private AddInviteeContract.AddInviteeView mView;
    private ContactService contactService;

    AddInviteePresenter() {
        contactService = new ContactServiceImpl();
    }

    @Override
    public void setView(AddInviteeContract.AddInviteeView appointmentdetailsView) {
        this.mView = appointmentdetailsView;
    }

    @Override
    public void onStop() {
        this.mView = null;
    }

    @Override
    public void fetchContactList(final Appointment appointment) {
        contactService.readContacts(new DataCallback<ArrayList<Contact>, String>() {
            @Override
            public void onSuccess(ArrayList<Contact> successResponse) {
                if(mView != null) {
                    ArrayList<Contact> filteredContacts = new ArrayList<>();
                    filteredContacts.addAll(successResponse);

                    for (Contact contact : successResponse) {
                        for (Contact contact1 : appointment.getInvitees()) {
                            if(contact.getId() == contact1.getId()) {
                                filteredContacts.remove(contact);
                            }
                        }
                    }
                    mView.setContactList(filteredContacts);
                }
            }

            @Override
            public void onFailure(String errorResponse) {
                if(mView != null) {
                    mView.showToast(errorResponse);
                }
            }
        });
    }
}
