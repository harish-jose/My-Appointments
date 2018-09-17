package com.harishjose.myappointments.screens.addInvitee;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.OnClickPositionCallback;
import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.databinding.FragmentAddInviteeBinding;
import com.harishjose.myappointments.databinding.FragmentAppointmentDetailsBinding;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.models.Contact;
import com.harishjose.myappointments.screens.appointmentDetail.InviteeListAdapter;
import com.harishjose.myappointments.screens.common.BaseFragment;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.ArrayList;


/**
 * Created by harish.jose on 15-09-2018.
 */
public class AddInviteeFragment extends BaseFragment implements AddInviteeContract.AddInviteeView, View.OnClickListener {

    private FragmentAddInviteeBinding binding;
    private AddInviteeContract.AddInviteePresenter presenter;
    private Appointment appointment;
    private ArrayList<Contact> contactArrayList;
    private InviteeListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentAddInviteeBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    protected void init() {
        presenter = new AddInviteePresenter();
        presenter.setView(this);
        contactArrayList = new ArrayList<>();
        if(getArguments() != null && getArguments().getSerializable(AppConstants.APPOINTMENT) != null) {
            appointment = (Appointment) getArguments().getSerializable(AppConstants.APPOINTMENT);
        } else {
            getActivity().onBackPressed();
            return;
        }
        binding.actionBar.btnHome.setVisibility(View.VISIBLE);
        binding.actionBar.btnHome.setOnClickListener(this);
        binding.actionBar.btnAction.setOnClickListener(this);
        setData();
    }

    private void setData() {
        binding.actionBar.tvToolbarTitle.setText(GeneralUtil.getString(R.string.add_invitee));

        if(adapter == null) {
            adapter = new InviteeListAdapter(contactArrayList, new OnClickPositionCallback() {
                @Override
                public void onClick(int position) {
                    appointment.getInvitees().add(contactArrayList.remove(position));
                    adapter.notifyDataSetChanged();
                }
            }, new InviteeListAdapter.OnProfileIconClickCallback() {
                @Override
                public void onClick(int position, View view) {
                }
            });
            adapter.setIsSimpleList(true);
        }
        binding.listInvitee.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.listInvitee.setAdapter(adapter);
        presenter.fetchContactList(appointment);
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
        }
    }


    @Override
    public void setContactList(ArrayList<Contact> dataList) {
        this.contactArrayList.clear();
        this.contactArrayList.addAll(dataList);
        this.contactArrayList.removeAll(appointment.getInvitees());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String message) {
        GeneralUtil.showLongToast(getActivity(), message);
    }

}
