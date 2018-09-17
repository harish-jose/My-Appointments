package com.harishjose.myappointments.screens.profilePopup;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.databinding.DialogProfileBinding;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.models.Contact;
import com.harishjose.myappointments.screens.common.BaseActivity;
import com.harishjose.myappointments.utils.GeneralUtil;
import com.harishjose.myappointments.utils.PermissionUtil;

/**
 * Created by harish.jose on 17-09-2018.
 */
public class ProfileActivity extends BaseActivity implements View.OnClickListener {

    private DialogProfileBinding binding;
    private Appointment appointment;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.dialog_profile);
        doInit();
    }

    @Override
    protected void doInit() {
        if(getIntent().getSerializableExtra(AppConstants.APPOINTMENT) != null) {
            appointment = (Appointment) getIntent().getSerializableExtra(AppConstants.APPOINTMENT);
            binding.tvName.setText(appointment.getOwner());
            binding.tvProfileIcon.setText(GeneralUtil.getInitials(appointment.getOwner()));

            if(appointment.getOwnerContactNumber() == null || appointment.getOwnerContactNumber().isEmpty()) {
                binding.btnCall.setVisibility(View.GONE);
                binding.btnSms.setVisibility(View.GONE);
            }
            if(appointment.getOwnerEmail() == null || appointment.getOwnerEmail().isEmpty()) {
                binding.btnEmail.setVisibility(View.GONE);
            }
        } else if(getIntent().getSerializableExtra(AppConstants.CONTACT) != null) {
            contact = (Contact) getIntent().getSerializableExtra(AppConstants.CONTACT);
            binding.tvName.setText(contact.getName());
            binding.tvProfileIcon.setText(GeneralUtil.getInitials(contact.getName()));
            if(contact.getPhoneNumber() == null || contact.getPhoneNumber().isEmpty()) {
                binding.btnCall.setVisibility(View.GONE);
                binding.btnSms.setVisibility(View.GONE);
            }
            if(contact.getEmail() == null || contact.getEmail().isEmpty()) {
                binding.btnEmail.setVisibility(View.GONE);
            }
        } else {
            onBackPressed();
        }
        binding.btnCall.setOnClickListener(this);
        binding.btnSms.setOnClickListener(this);
        binding.btnEmail.setOnClickListener(this);
        binding.lytOutSid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnCall) {
            if(appointment != null) {
                makePhoneCall(appointment.getOwnerContactNumber());
            } else if(contact != null) {
                makePhoneCall(contact.getPhoneNumber());
            }
        } else if(v == binding.btnSms) {
            if(appointment != null) {
                sendSMS(appointment.getOwnerContactNumber());
            } else if(contact != null) {
                sendSMS(contact.getPhoneNumber());
            }
        } else if(v == binding.btnEmail) {
            if(appointment != null) {
                sendEmail(appointment.getOwnerEmail());
            } else if(contact != null) {
                sendEmail(contact.getEmail());
            }
        } else if(v == binding.lytOutSid) {
            onBackPressed();
        }
    }

    @SuppressLint("MissingPermission")
    private void makePhoneCall(String phoneNumber) {
        if(PermissionUtil.checkRequestPhoneCall(this)) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber.trim()));
            startActivity(callIntent);
        }
    }

    public void sendSMS(String phoneNumber)
    {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", phoneNumber, null)));
    }

    public void sendEmail(String email) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PermissionUtil.CALL_REQUEST)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                makePhoneCall(appointment.getOwnerContactNumber());
            } else {
                GeneralUtil.showLongToast(this, GeneralUtil.getString(R.string.phone_call_permission_denied));
            }
        }
    }
}
