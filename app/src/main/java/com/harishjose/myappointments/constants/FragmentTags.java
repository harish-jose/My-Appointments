package com.harishjose.myappointments.constants;

/**
 * Created by harish on 14-09-18.
 */

public enum FragmentTags {
    APPOINTMENTS_LIST_FRAGMENT("AppointmentsListFragment"),
    ADD_INVITEE_FRAGMENT("AddInviteeFragment"),
    APPOINTMENT_DETAILS_FRAGMENT("AppointmentDetailsFragment");

    private final String text;

    /**
     * @param text string value
     */
    private FragmentTags(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public static FragmentTags getTagFromString(String code){
        try{
            for(FragmentTags e : FragmentTags.values()){
                if(code.equals(e.text)) return e;
            }
        }
        catch (Exception e){

        }

        return null;
    }


}
