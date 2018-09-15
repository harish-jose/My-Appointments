package com.harishjose.myappointments.screens.appointmentList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.OnClickPositionCallback;
import com.harishjose.myappointments.models.Appointment;

import java.util.ArrayList;


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
        TextView tvSubject, tvLocation;
        RelativeLayout lytItem;
        public AppointmentItemHolder(View view){
            super(view);
            tvSubject = view.findViewById(R.id.tv_subject);
            tvLocation = view.findViewById(R.id.tv_location);
            lytItem = view.findViewById(R.id.lyt_item);
        }
    }
}
