package com.example.samim.bottomnavigationwithfragment.Models;

/**
 * Created by SAMIM on 3/26/2018.
 */

public class Product {
    private String mquantity;
    private String miphonemodel;

    public Product(String mQuantity, String mIphoneModel) {
        this.mquantity = mQuantity;
        this.miphonemodel = mIphoneModel;
    }
    public Product(){
        //default constructor
    }

    public String getMquantity() {
        return mquantity;
    }

    public void setMquantity(String mquantity) {
        this.mquantity = mquantity;
    }

    public String getMiphonemodel() {
        return miphonemodel;
    }

    public void setMiphonemodel(String miphonemodel) {
        this.miphonemodel = miphonemodel;
    }
}
