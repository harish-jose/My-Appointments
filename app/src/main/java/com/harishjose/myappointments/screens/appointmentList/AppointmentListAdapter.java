package com.harishjose.myappointments.screens.appointmentList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.OnClickPositionCallback;
import com.harishjose.myappointments.constants.AppConstants;
import com.harishjose.myappointments.models.Appointment;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * Created by harish.jose on 14-09-2018.
 */

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.AppointmentItemHolder>{
    private ArrayList<Appointment> itemArrayList;
    private OnClickPositionCallback callback;

    public AppointmentListAdapter(ArrayList<Appointment> dataList, OnClickPositionCallback positionCallback){
        this.itemArrayList = dataList;
        this.callback = positionCallback;
    }

    @NonNull
    @Override
    public AppointmentItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_appointment_item, parent, false);
        return new AppointmentItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentItemHolder holder, final int position) {
        if(position > 0 && GeneralUtil.checkForSameDay(itemArrayList.get(position-1).getActivityStartDate(), itemArrayList.get(position).getActivityStartDate())) {
            holder.lytHeader.setVisibility(View.GONE);
        } else {
            holder.lytHeader.setVisibility(View.VISIBLE);
            holder.tvDate.setText(GeneralUtil.formatDateTime(itemArrayList.get(position).getActivityStartDate(), AppConstants.MMM_DD_YYYY));
        }
        holder.tvSubject.setText(itemArrayList.get(position).getSubject());
        holder.tvLocation.setText(itemArrayList.get(position).getLocation());
        holder.lytItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList != null ? itemArrayList.size() : 0;
    }

    class AppointmentItemHolder extends RecyclerView.ViewHolder{
        TextView tvDay, tvDate, tvSubject, tvLocation, tvTimeSlot, tvTime, tvAmPm, tvProfileIcon,
                tvOwnerName, tvAccountName;
        RelativeLayout lytItem, lytOwnerInfo;
        LinearLayout lytHeader, lytTime;
        public AppointmentItemHolder(View view){
            super(view);
            tvDay = view.findViewById(R.id.tv_day);
            tvDate = view.findViewById(R.id.tv_date);
            tvTime = view.findViewById(R.id.tv_time);
            tvAmPm = view.findViewById(R.id.tv_am_pm);
            tvSubject = view.findViewById(R.id.tv_subject);
            tvLocation = view.findViewById(R.id.tv_location);
            tvTimeSlot = view.findViewById(R.id.tv_time_slot);
            tvProfileIcon = view.findViewById(R.id.profile_icon);
            tvOwnerName = view.findViewById(R.id.tv_owner_name);
            tvAccountName = view.findViewById(R.id.tv_account_name);

            lytHeader = view.findViewById(R.id.lyt_header);
            lytTime = view.findViewById(R.id.lyt_time);
            lytOwnerInfo = view.findViewById(R.id.lyt_owner_info);
            lytItem = view.findViewById(R.id.lyt_item);
        }
    }
}
