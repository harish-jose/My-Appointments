package com.harishjose.myappointments.screens.appointmentDetail;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.OnClickPositionCallback;
import com.harishjose.myappointments.components.CustomEditText;
import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.constants.FragmentTags;
import com.harishjose.myappointments.databinding.FragmentAppointmentDetailsBinding;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.models.Contact;
import com.harishjose.myappointments.screens.MainActivity;
import com.harishjose.myappointments.screens.appointmentList.AppointmentListPresenter;
import com.harishjose.myappointments.screens.common.BaseFragment;
import com.harishjose.myappointments.screens.profilePopup.ProfileActivity;
import com.harishjose.myappointments.utils.DateTimePickerUtil;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.Calendar;


/**
 * Created by harish.jose on 15-09-2018.
 */
public class AppointmentDetailsFragment extends BaseFragment implements AppointmentDetailsContract.AppointmentDetailsView, View.OnClickListener {

    private FragmentAppointmentDetailsBinding binding;
    private AppointmentDetailsContract.AppointmentDetailsPresenter presenter;
    private Appointment appointment;
    private InviteeListAdapter adapter;
    private String tempStartdate, tempEndDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentAppointmentDetailsBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    protected void init() {
        presenter = new AppointmentDetailsPresenter();
        presenter.setView(this);
        if(getArguments() != null && getArguments().getSerializable(AppConstants.APPOINTMENT) != null) {
            appointment = (Appointment) getArguments().getSerializable(AppConstants.APPOINTMENT);
        } else {
            getActivity().onBackPressed();
            return;
        }
        binding.actionBar.btnHome.setVisibility(View.VISIBLE);
        binding.actionBar.btnAction.setVisibility(View.VISIBLE);
        binding.actionBar.btnHome.setOnClickListener(this);
        binding.actionBar.btnAction.setOnClickListener(this);
        binding.btnAddInvitee.setOnClickListener(this);
        binding.btnSave.setOnClickListener(this);
        binding.btnCancel.setOnClickListener(this);
        setData();
    }

    private void setData() {
        binding.actionBar.tvToolbarTitle.setText(appointment.getSubject());
        binding.etSubject.setText(appointment.getSubject());
        binding.etLocation.setText(appointment.getLocation());
        binding.etType.setText(appointment.getActivityType());
        binding.etStart.setText(GeneralUtil.formatDateTime(appointment.getActivityStartDate(), AppConstants.EEEE_M_d_yyyy_h_mm_a).replace("am", "AM").replace("pm", "PM"));
        binding.etEnd.setText(GeneralUtil.formatDateTime(appointment.getActivityEndDate(), AppConstants.EEEE_M_d_yyyy_h_mm_a).replace("am", "AM").replace("pm", "PM"));
        binding.etAccount.setText(appointment.getAccountName());
        binding.etOpportunity.setText(appointment.getOpportunityName());
        binding.etLead.setText(appointment.getLeadName());
        binding.etPrimaryContact.setText(appointment.getPrimaryContactName());
        binding.etDescription.setText(appointment.getDescription());
        if(adapter == null) {
            adapter = new InviteeListAdapter(appointment.getInvitees(), new OnClickPositionCallback() {
                @Override
                public void onClick(int position) {
                    appointment.getInvitees().remove(position);
                    adapter.notifyDataSetChanged();
                    presenter.updateAppointmentData(appointment);
                }
            }, new InviteeListAdapter.OnProfileIconClickCallback() {
                @Override
                public void onClick(int position, View view) {
                    openProfilePopup(appointment.getInvitees().get(position), view);
                }
            });
        }
        binding.listInvitee.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.listInvitee.setAdapter(adapter);
        tempStartdate = appointment.getActivityStartDate();
        tempEndDate = appointment.getActivityEndDate();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    public void onClick(View v) {
        if(v == binding.actionBar.btnHome) {
            getActivity().onBackPressed();
        } else if(v == binding.actionBar.btnAction) {
            toggleEditMode(true);
        } else if(v == binding.btnAddInvitee) {
            navigateToAddInvitee(appointment);
        } else if(v == binding.btnSave) {
            if(validateFields()) {
                updateAppointment();
            }
        } else if(v == binding.btnCancel) {
            toggleEditMode(false);
        } else if(v == binding.etStart) {
            new DateTimePickerUtil(getActivity(), null, GeneralUtil.parseDateTimeToCalendar(tempStartdate), new DateTimePickerUtil.OnSelectedListener() {
                @Override
                public void onSelected(long timeInMillis) {
                    binding.etStart.setText(GeneralUtil.formatDateTime(timeInMillis, AppConstants.EEEE_M_d_yyyy_h_mm_a));
                    tempStartdate = GeneralUtil.formatDateTime(timeInMillis, AppConstants.DATETIMEFORMAT);
                    if(GeneralUtil.parseDateTimeToDate(tempEndDate).getTime() <= timeInMillis) {
                        binding.etEnd.setText("");
                    }
                }
            }).showPicker();
        } else if(v == binding.etEnd) {
            new DateTimePickerUtil(getActivity(), GeneralUtil.parseDateTimeToCalendar(tempStartdate), GeneralUtil.parseDateTimeToCalendar(tempEndDate), new DateTimePickerUtil.OnSelectedListener() {
                @Override
                public void onSelected(long timeInMillis) {
                    binding.etEnd.setText(GeneralUtil.formatDateTime(timeInMillis, AppConstants.EEEE_M_d_yyyy_h_mm_a));
                    tempEndDate = GeneralUtil.formatDateTime(timeInMillis, AppConstants.DATETIMEFORMAT);
                }
            }).showPicker();
        }
    }

    @Override
    public void setAppointment(Appointment data) {
        appointment = data;
        setData();
    }

    @Override
    public void showToast(String message) {
        GeneralUtil.showLongToast(getActivity(), message);
    }

    @Override
    public void toggleEditMode(boolean enable) {
        if(enable) {
            binding.actionBar.btnAction.setVisibility(View.INVISIBLE);
            binding.etSubject.setEnabled(true);
            binding.etLocation.setEnabled(true);
            binding.etType.setEnabled(true);
            binding.etStart.setOnClickListener(this);
            binding.etEnd.setOnClickListener(this);
            binding.etAccount.setEnabled(true);
            binding.etOpportunity.setEnabled(true);
            binding.etLead.setEnabled(true);
            binding.etPrimaryContact.setEnabled(true);
            binding.etDescription.setEnabled(true);
            binding.lytInviteeHeader.setVisibility(View.GONE);
            binding.listInvitee.setVisibility(View.GONE);
            binding.lytSaveButton.setVisibility(View.VISIBLE);
            binding.etSubject.requestFocus();
        } else {
            binding.actionBar.btnAction.setVisibility(View.VISIBLE);
            binding.etSubject.setEnabled(false);
            binding.etLocation.setEnabled(false);
            binding.etType.setEnabled(false);
            binding.etStart.setOnClickListener(null);
            binding.etEnd.setOnClickListener(null);
            binding.etAccount.setEnabled(false);
            binding.etOpportunity.setEnabled(false);
            binding.etLead.setEnabled(false);
            binding.etPrimaryContact.setEnabled(false);
            binding.etDescription.setEnabled(false);
            binding.lytInviteeHeader.setVisibility(View.VISIBLE);
            binding.listInvitee.setVisibility(View.VISIBLE);
            binding.lytSaveButton.setVisibility(View.GONE);
            setData();
        }
    }

    private boolean validateFields() {
        if(!binding.etSubject.isHaveValidData()) {
            GeneralUtil.showLongToast(getActivity(), R.string.error_please_fill_subject);
            return false;
        } else if(!binding.etStart.isHaveValidData()) {
            GeneralUtil.showLongToast(getActivity(), R.string.error_please_select_start_date_time);
            return false;
        } else if(!binding.etEnd.isHaveValidData()) {
            GeneralUtil.showLongToast(getActivity(), R.string.error_please_select_end_date_time);
            return false;
        }
        return true;
    }

    private void updateAppointment() {
        appointment.setSubject(binding.etSubject.getText());
        appointment.setLocation(binding.etLocation.getText());
        appointment.setActivityType(binding.etType.getText());
        appointment.setActivityStartDate(tempStartdate);
        appointment.setActivityEndDate(tempEndDate);
        appointment.setAccountName(binding.etAccount.getText());
        appointment.setOpportunityName(binding.etOpportunity.getText());
        appointment.setLeadName(binding.etLead.getText());
        appointment.setPrimaryContactName(binding.etPrimaryContact.getText());
        presenter.updateAppointmentData(appointment);
    }

    /**
     * Show the popup with Call, sms, and email actions
     * @param contact
     * @param sharedView
     */
    private void openProfilePopup(Contact contact, View sharedView) {
        Intent intent = new Intent(new Intent(getActivity(), ProfileActivity.class));
        intent.putExtra(AppConstants.CONTACT, contact);
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(getActivity(), sharedView, "profile_transition");
        startActivity(intent, options.toBundle());
    }

    private void navigateToAddInvitee(Appointment appointment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.APPOINTMENT, appointment);
        ((MainActivity)getActivity()).loadFragment(FragmentTags.ADD_INVITEE_FRAGMENT, bundle);
    }
}
