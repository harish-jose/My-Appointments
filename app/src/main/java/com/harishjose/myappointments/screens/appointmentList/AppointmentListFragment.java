package com.harishjose.myappointments.screens.appointmentList;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.harishjose.myappointments.callbacks.OnClickPositionCallback;
import com.harishjose.myappointments.databinding.FragmentAppointmentListBinding;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.screens.common.BaseFragment;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.ArrayList;


/**
 * Created by harish.jose on 14-09-2018.
 */
public class AppointmentListFragment extends BaseFragment implements AppointmentListContract.AppointmentListView{

    private FragmentAppointmentListBinding binding;
    private AppointmentListContract.AppointmentListPresenter presenter;
    private AppointmentListAdapter adapter;
    private ArrayList<Appointment> appointmentArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentAppointmentListBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    protected void init() {
        presenter = new AppointmentListPresenter();
        appointmentArrayList = new ArrayList<>();
        presenter.setView(this);
        if(adapter == null) {
            adapter = new AppointmentListAdapter(appointmentArrayList, new OnClickPositionCallback() {
                @Override
                public void onClick(int position) {

                }
            });
        }
        binding.listAppointments.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.listAppointments.setAdapter(adapter);
        presenter.fetchAppointmentList();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void setAppointmentList(ArrayList<Appointment> dataList) {
        if(dataList != null) {
            appointmentArrayList.clear();
            appointmentArrayList.addAll(dataList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showToast(String message) {
        GeneralUtil.showLongToast(getActivity(), message);
    }


    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
