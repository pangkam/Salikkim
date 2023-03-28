package com.salikkim.bazar.Models;

public class Order{
    private String S_id,Images,Thumbnail,Title,Color,Size,Address,Seller_Name,Date,Payment_Screenshot,Desc;
    private int O_Id,P_Id,Quantity,Status;
    private double Price,Sale_Price,Discount,Shipping_Charge;

    public String getS_id() {
        return S_id;
    }

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

    public String getSeller_Name() {
        return Seller_Name;
    }

    public String getDate() {
        return Date;
    }

    public String getPayment_Screenshot() {
        return Payment_Screenshot;
    }

    public String getDesc() {
        return Desc;
    }

    public int getO_Id() {
        return O_Id;
    }

    public int getP_Id() {
        return P_Id;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getStatus() {
        return Status;
    }

    public double getPrice() {
        return Price;
    }

    public double getSale_Price() {
        return Sale_Price;
    }

    public double getDiscount() {
        return Discount;
    }

    public double getShipping_Charge() {
        return Shipping_Charge;
    }
}
