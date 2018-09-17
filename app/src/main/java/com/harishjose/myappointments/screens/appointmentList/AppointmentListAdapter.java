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
    private OnProfileIconClickCallback profileClickCallback;

    public AppointmentListAdapter(ArrayList<Appointment> dataList, OnClickPositionCallback positionCallback, OnProfileIconClickCallback profileClickCallback){
        this.itemArrayList = dataList;
        this.callback = positionCallback;
        this.profileClickCallback = profileClickCallback;
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
        Calendar startDateCal = GeneralUtil.parseDateTimeToCalendar(itemArrayList.get(position).getActivityStartDate());
        Calendar endDateCal = GeneralUtil.parseDateTimeToCalendar(itemArrayList.get(position).getActivityEndDate());
        if(position > 0 && GeneralUtil.checkForSameDay(itemArrayList.get(position-1).getActivityStartDate(), itemArrayList.get(position).getActivityStartDate())) {
            holder.lytHeader.setVisibility(View.GONE);
        } else {
            holder.lytHeader.setVisibility(View.VISIBLE);
            holder.tvDate.setText(GeneralUtil.formatDateTime(startDateCal.getTime(), AppConstants.MMM_DD_YYYY));
            if(GeneralUtil.isToday(startDateCal.getTimeInMillis())) {
                holder.tvDay.setVisibility(View.VISIBLE);
                holder.tvDay.setText(GeneralUtil.getString(R.string.today));
            } else if(GeneralUtil.isTomorrow(startDateCal.getTimeInMillis())) {
                holder.tvDay.setVisibility(View.VISIBLE);
                holder.tvDay.setText(GeneralUtil.getString(R.string.tomorrow));
            } else {
                holder.tvDay.setVisibility(View.GONE);
            }
        }
        holder.tvTime.setText(GeneralUtil.formatDateTime(startDateCal.getTime(), AppConstants.hh_mm));
        holder.tvAmPm.setText(GeneralUtil.formatDateTime(startDateCal.getTime(), AppConstants.am_pm));
        holder.tvSubject.setText(itemArrayList.get(position).getSubject());
        holder.tvLocation.setText(itemArrayList.get(position).getLocation());
        String timeSlot = GeneralUtil.formatDateTime(startDateCal.getTime(), AppConstants.hh_mm_am_pm) + " - " +
                GeneralUtil.formatDateTime(endDateCal.getTime(), AppConstants.hh_mm_am_pm);
        holder.tvTimeSlot.setText(timeSlot);

        holder.tvOwnerName.setText(itemArrayList.get(position).getOwner());
        holder.tvProfileIcon.setText(GeneralUtil.getInitials(itemArrayList.get(position).getOwner()));
        holder.tvAccountName.setText(itemArrayList.get(position).getAccountName());

        if(GeneralUtil.isTimeSlotNow(startDateCal.getTime(), endDateCal.getTime()) ||
                (position > 0 && !itemArrayList.get(position-1).isSelected() && GeneralUtil.isUpcomingTime(startDateCal.getTime()))) {
            holder.tvLocation.setVisibility(View.VISIBLE);
            holder.tvTimeSlot.setVisibility(View.VISIBLE);
            holder.lytOwnerInfo.setBackgroundColor(GeneralUtil.getColor(R.color.white));
            holder.lytItem.setBackgroundColor(GeneralUtil.getColor(R.color.selected));
            holder.lytTime.setBackgroundColor(GeneralUtil.getColor(R.color.selected));
            holder.tvTime.setTextColor(GeneralUtil.getColor(R.color.whiteTransparent50));
            holder.tvAmPm.setTextColor(GeneralUtil.getColor(R.color.whiteTransparent50));
            itemArrayList.get(position).setSelected(true);
        } else {
            holder.tvLocation.setVisibility(View.GONE);
            holder.tvTimeSlot.setVisibility(View.GONE);
            holder.lytOwnerInfo.setBackgroundColor(GeneralUtil.getColor(R.color.lightGrey));
            holder.lytItem.setBackgroundColor(GeneralUtil.getColor(R.color.dividerColor));
            holder.lytTime.setBackgroundColor(GeneralUtil.getColor(R.color.white));
            holder.tvTime.setTextColor(GeneralUtil.getColor(R.color.primaryText));
            holder.tvAmPm.setTextColor(GeneralUtil.getColor(R.color.secondaryText));
        }

        holder.tvProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileClickCallback.onClick(position, v);
            }
        });

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

    public interface OnProfileIconClickCallback {
        void onClick(int position, View view);
    }
}
