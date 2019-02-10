package com.example.samim.bottomnavigationwithfragment;

import android.support.annotation.NonNull;

public class AppointmentId {
    public String appointmentId;

    public <T extends AppointmentId> T withId(@NonNull final String id){
        this.appointmentId = id;
        return (T) this;
    }
}
