package com.harishjose.myappointments.screens.appointmentDetail;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.harishjose.myappointments.R;
import com.harishjose.myappointments.callbacks.OnClickPositionCallback;
import com.harishjose.myappointments.models.Contact;
import com.harishjose.myappointments.utils.GeneralUtil;

import java.util.ArrayList;


/**
 * Created by harish.jose on 17-09-2018.
 */

public class InviteeListAdapter extends RecyclerView.Adapter<InviteeListAdapter.InviteeItemHolder>{
    private ArrayList<Contact> itemArrayList;
    private OnClickPositionCallback callback;
    private OnProfileIconClickCallback profileClickCallback;
    private boolean isSimpleList;

    public InviteeListAdapter(ArrayList<Contact> dataList, OnClickPositionCallback positionCallback, OnProfileIconClickCallback profileClickCallback){
        this.itemArrayList = dataList;
        this.callback = positionCallback;
        this.profileClickCallback = profileClickCallback;
    }

    @NonNull
    @Override
    public InviteeItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_invitee_item, parent, false);
        return new InviteeItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InviteeItemHolder holder, final int position) {
        holder.tvName.setText(itemArrayList.get(position).getName());
        holder.tvProfileIcon.setText(GeneralUtil.getInitials(itemArrayList.get(position).getName()));
        holder.tvDesignation.setText(itemArrayList.get(position).getJobTitle());

        holder.tvProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileClickCallback != null) {
                    profileClickCallback.onClick(position, v);
                }
            }
        });

        if(isSimpleList) {
            holder.tvProfileIcon.setVisibility(View.GONE);
            holder.tvDesignation.setVisibility(View.GONE);
            holder.btnRemove.setVisibility(View.GONE);
            holder.lytInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(position);
                }
            });
        } else {
            holder.tvProfileIcon.setVisibility(View.VISIBLE);
            holder.tvDesignation.setVisibility(View.VISIBLE);
            holder.btnRemove.setVisibility(View.VISIBLE);
            holder.btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callback.onClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList != null ? itemArrayList.size() : 0;
    }

    class InviteeItemHolder extends RecyclerView.ViewHolder{
        TextView tvProfileIcon, tvName, tvDesignation;
        ImageButton btnRemove;
        RelativeLayout lytInfo;
        public InviteeItemHolder(View view){
            super(view);
            tvProfileIcon = view.findViewById(R.id.profile_icon);
            tvName = view.findViewById(R.id.tv_name);
            tvDesignation = view.findViewById(R.id.tv_designation);
            btnRemove = view.findViewById(R.id.btn_remove);
            lytInfo = view.findViewById(R.id.lyt_invitee_info);
        }
    }

    public void setIsSimpleList(boolean isSimpleList) {
        this.isSimpleList = isSimpleList;
    }

    public interface OnProfileIconClickCallback {
        void onClick(int position, View view);
    }
}
