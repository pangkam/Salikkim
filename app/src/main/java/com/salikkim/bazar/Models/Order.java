package com.salikkim.bazar.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Order{
    private String Images,Thumbnail,Title,Color,Size,Address,S_Name,Date,Payment_Screenshot;
    private int O_Id,P_Id,S_id,Quantity,Status;
    private double Total_Price,Shipping_Charge;

    public String getImages() {
        return Images;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public String getColor() {
        return Color;
    }

    public String getSize() {
        return Size;
    }

    public String getAddress() {
        return Address;
    }

    public String getS_Name() {
        return S_Name;
    }

    public String getDate() {
        return Date;
    }

    public String getPayment_Screenshot() {
        return Payment_Screenshot;
    }

    public int getO_Id() {
        return O_Id;
    }

    public int getP_Id() {
        return P_Id;
    }

    public int getS_id() {
        return S_id;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getStatus() {
        return Status;
    }

    public double getTotal_Price() {
        return Total_Price;
    }

    public double getShipping_Charge() {
        return Shipping_Charge;
    }
}
