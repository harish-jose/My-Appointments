package com.harishjose.myappointments.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by harish.jose on 15-09-2018.
 */
public class Appointment implements Serializable{
    @SerializedName("AppointmentId")
    private long appointmentId;
    @SerializedName("ActivityStartDate")
    private String activityStartDate;
    @SerializedName("ActivityEndDate")
    private String activityEndDate;
    @SerializedName("Subject")
    private String subject;
    @SerializedName("ActivityType")
    private String activityType;
    @SerializedName("Location")
    private String location;
    @SerializedName("Owner")
    private String owner;
    @SerializedName("OwnerEmail")
    private String ownerEmail;
    @SerializedName("OwnerContactNumber")
    private String ownerContactNumber;
    @SerializedName("PrimaryContactName")
    private String primaryContactName;
    @SerializedName("LeadName")
    private String leadName;
    @SerializedName("AccountName")
    private String accountName;
    @SerializedName("OpportunityName")
    private String opportunityName;
    @SerializedName("Description")
    private String description;
    @SerializedName("invitees")
    private ArrayList<Contact> invitees;

    private boolean isSelected;

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getActivityStartDate() {
        return activityStartDate;
    }

    public void setActivityStartDate(String activityStartDate) {
        this.activityStartDate = activityStartDate;
    }

    public String getActivityEndDate() {
        return activityEndDate;
    }

    public void setActivityEndDate(String activityEndDate) {
        this.activityEndDate = activityEndDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerContactNumber() {
        return ownerContactNumber;
    }

    public void setOwnerContactNumber(String ownerContactNumber) {
        this.ownerContactNumber = ownerContactNumber;
    }

    public String getPrimaryContactName() {
        return primaryContactName;
    }

    public void setPrimaryContactName(String primaryContactName) {
        this.primaryContactName = primaryContactName;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getOpportunityName() {
        return opportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        this.opportunityName = opportunityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Contact> getInvitees() {
        return invitees;
    }

    public void setInvitees(ArrayList<Contact> invitees) {
        this.invitees = invitees;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
