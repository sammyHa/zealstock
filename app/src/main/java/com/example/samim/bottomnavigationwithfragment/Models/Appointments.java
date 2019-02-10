package com.example.samim.bottomnavigationwithfragment.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.samim.bottomnavigationwithfragment.AppointmentId;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

@IgnoreExtraProperties
public class Appointments {

    private @ServerTimestamp Date timestamp;
    private String name;
    private String time;
    private String date;
    private String iphoneModel;
    private String price;
    private String color;
    private String address;
    private String quantity;
    private String phoneNumber;
    private String appointment_id;
    private String user_id;

    public Appointments(
            String name,
            String time,
            String date,
            String iphoneModel,
            String price,
            String color,
            String address,
            String quantity,
            String phoneNumber,
            String appointment_id,
            String user_id,
            Date timestamp
    ) {
        this.name = name;
        this.time = time;
        this.date = date;
        this.iphoneModel = iphoneModel;
        this.price = price;
        this.color = color;
        this.address = address;
        this.quantity = quantity;
        this.phoneNumber = phoneNumber;
        this.appointment_id = appointment_id;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    public Appointments(){
        //default constructor needed for the firestore database or it will crush
    }

    public Appointments(
            String time,
            String date,
            String iphoneModel,
            String price,
            String color,
            String address,
            String quantity,
            String phoneNumber,
            String appointment_id,
            String user_id
    ) {
        this.time = time;
        this.date = date;
        this.iphoneModel = iphoneModel;
        this.price = price;
        this.color = color;
        this.address = address;
        this.quantity = quantity;
        this.phoneNumber = phoneNumber;
        this.appointment_id = appointment_id;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIphoneModel() {
        return iphoneModel;
    }

    public void setIphoneModel(String iphoneModel) {
        this.iphoneModel = iphoneModel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
