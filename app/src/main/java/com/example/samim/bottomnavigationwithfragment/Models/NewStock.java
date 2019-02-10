package com.example.samim.bottomnavigationwithfragment.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;

public class NewStock implements Parcelable{

    private String mModel;
    private String mColor;
    private String mPrice;
    private String mQuantity;
    private String stock_id;
    private String user_id;

    public NewStock(){

    }

    public NewStock(String mModel, String mColor, String mPrice, String mQuantity, String stock_id, String user_id) {
        this.mModel = mModel;
        this.mColor = mColor;
        this.mPrice = mPrice;
        this.mQuantity = mQuantity;
        this.stock_id = stock_id;
        this.user_id = user_id;
    }



    protected NewStock(Parcel in) {
        mModel = in.readString();
        mColor = in.readString();
        mPrice = in.readString();
        mQuantity = in.readString();
        stock_id = in.readString();
        user_id = in.readString();
    }


    public static final Creator<NewStock> CREATOR = new Creator<NewStock>() {
        @Override
        public NewStock createFromParcel(Parcel in) {

            return new NewStock(in);
        }

        @Override
        public NewStock[] newArray(int size) {
            return new NewStock[size];
        }
    };


    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mModel);
        parcel.writeString(mColor);
        parcel.writeString(mPrice);
        parcel.writeString(mQuantity);
        parcel.writeString(stock_id);
        parcel.writeString(user_id);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public String getmModel() {
        return mModel;
    }

    public void setmModel(String mModel) {
        this.mModel = mModel;
    }

    public String getmColor() {
        return mColor;
    }

    public void setmColor(String mColor) {
        this.mColor = mColor;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(String mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getStock_id() {
        return stock_id;
    }

    public void setStock_id(String stock_id) {
        this.stock_id = stock_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


}
