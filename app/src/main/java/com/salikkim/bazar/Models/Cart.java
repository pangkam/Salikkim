package com.salikkim.bazar.Models;

public class Cart {
    private String Thumbnail,Title,Color,Size,COD,Addresses,Seller_Name,Seller_Contacts,Seller_Accounts;
    private int C_Id,P_Id,S_id,Quantity,Total_Quantity;
    private double Price,Sale_Price,Discount;

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

    public String getCOD() {
        return COD;
    }

    public String getAddresses() {
        return Addresses;
    }

    public String getSeller_Name() {
        return Seller_Name;
    }

    public String getSeller_Contacts() {
        return Seller_Contacts;
    }

    public String getSeller_Accounts() {
        return Seller_Accounts;
    }

    public int getC_Id() {
        return C_Id;
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

    public int getTotal_Quantity() {
        return Total_Quantity;
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
}
