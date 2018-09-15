package com.harishjose.myappointments.screens.appointmentList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.harishjose.myappointments.screens.common.BaseFragment;
import com.harishjose.myappointments.utils.GeneralUtil;


/**
 * Created by harish.jose on 14-09-2018.
 */
public class AppointmentListFragment extends BaseFragment implements AppointmentListContract.AppointmentListView{

    private AppointmentListContract.AppointmentListPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //binding = FragmentDeliveriesListBinding.inflate(inflater, container, false);
        init();
        return getView();
    }

    @Override
    protected void init() {
        presenter = new AppointmentListPresenter();
        presenter.setView(this);

        presenter.fetchAppointmentList();
    }

    @Override
    public void onResume() {
        super.onResume();
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
