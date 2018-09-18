package com.harishjose.myappointments.screens.addInvitee;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.DataCallback;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.models.Contact;
import com.harishjose.myappointments.services.AppointmentService;
import com.harishjose.myappointments.services.AppointmentServiceImpl;
import com.harishjose.myappointments.services.ContactService;
import com.harishjose.myappointments.services.ContactServiceImpl;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.ArrayList;


/**
 * Created by harish.jose on 17-09-2018.
 */
public class AddInviteePresenter implements AddInviteeContract.AddInviteePresenter {

    private AddInviteeContract.AddInviteeView mView;
    private ContactService contactService;
    private AppointmentService appointmentService;

    AddInviteePresenter() {
        contactService = new ContactServiceImpl();
        appointmentService = new AppointmentServiceImpl();
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

    @Override
    public void updateAppointmentData(final Appointment appointment) {
        appointmentService.readAppointments(new DataCallback<ArrayList<Appointment>, String>() {
            @Override
            public void onSuccess(ArrayList<Appointment> successResponse) {
                int position;
                for (position=0; position<successResponse.size(); position++) {
                    if(successResponse.get(position).getAppointmentId() == appointment.getAppointmentId()) {
                        break;
                    }
                }
                if(position < successResponse.size()) {
                    successResponse.remove(position);
                    successResponse.add(position, appointment);
                    appointmentService.updateAppointments(successResponse, new DataCallback<String, String>() {
                        @Override
                        public void onSuccess(String successResponse) {
                            if(mView != null) {
                                mView.showToast(GeneralUtil.getString(R.string.invitee_added_successfully));
                            }
                        }

                        @Override
                        public void onFailure(String errorResponse) {
                            if(mView != null) {
                                mView.showToast(GeneralUtil.getString(R.string.invitee_add_failed));
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(String errorResponse) {
                if(mView != null) {
                    mView.showToast(GeneralUtil.getString(R.string.invitee_add_failed));
                }
            }
        });
    }
}
