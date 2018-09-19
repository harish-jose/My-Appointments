package com.harishjose.myappointments.screens.appointmentList;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.OnClickPositionCallback;
import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.constants.FragmentTags;
import com.harishjose.myappointments.databinding.FragmentAppointmentListBinding;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.screens.MainActivity;
import com.harishjose.myappointments.screens.common.BaseFragment;
import com.harishjose.myappointments.screens.profilePopup.ProfileActivity;
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
    private RecyclerView.SmoothScroller smoothScroller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentAppointmentListBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    @Override
    protected void init() {
        binding.actionBar.tvToolbarTitle.setText(GeneralUtil.getString(R.string.appointments));
        presenter = new AppointmentListPresenter();
        smoothScroller = new LinearSmoothScroller(getActivity()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        appointmentArrayList = new ArrayList<>();
        presenter.setView(this);
        adapter = new AppointmentListAdapter(appointmentArrayList, new OnClickPositionCallback() {
            @Override
            public void onClick(int position) {
                navigateToDetailsScreen(appointmentArrayList.get(position));
            }
        }, new AppointmentListAdapter.OnProfileIconClickCallback() {
            @Override
            public void onClick(int position, View view) {
                openProfilePopup(appointmentArrayList.get(position), view);
            }
        });
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

    /**
     * Show the popup with Call, sms, and email actions
     * @param appointment
     * @param sharedView
     */
    private void openProfilePopup(Appointment appointment ,View sharedView) {
        Intent intent = new Intent(new Intent(getActivity(), ProfileActivity.class));
        intent.putExtra(AppConstants.APPOINTMENT, appointment);
        ActivityOptions options = ActivityOptions
                .makeSceneTransitionAnimation(getActivity(), sharedView, "profile_transition");
        startActivity(intent, options.toBundle());
    }

    private void navigateToDetailsScreen(Appointment appointment) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.APPOINTMENT, appointment);
        ((MainActivity)getActivity()).loadFragment(FragmentTags.APPOINTMENT_DETAILS_FRAGMENT, bundle);
    }
}
