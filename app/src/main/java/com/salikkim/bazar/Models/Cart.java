package com.salikkim.bazar.Models;

public class Cart {
    private String Thumbnail,Title,Color,Size,Address,S_id,Seller_Name, Seller_Acc;
    private int C_Id,P_Id,Quantity,Total_Quantity;
    private double Price,Sale_Price,Discount,Shipping_Charge;
    private boolean isAvailable;

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getS_id() {
        return S_id;
    }

    public void setS_id(String s_id) {
        S_id = s_id;
    }

    public String getSeller_Name() {
        return Seller_Name;
    }

    public void setSeller_Name(String seller_Name) {
        Seller_Name = seller_Name;
    }

    public String getSeller_Acc() {
        return Seller_Acc;
    }

    public void setSeller_Acc(String seller_Acc) {
        Seller_Acc = seller_Acc;
    }

    public int getC_Id() {
        return C_Id;
    }

    public void setC_Id(int c_Id) {
        C_Id = c_Id;
    }

    public int getP_Id() {
        return P_Id;
    }

    public void setP_Id(int p_Id) {
        P_Id = p_Id;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getTotal_Quantity() {
        return Total_Quantity;
    }

    public void setTotal_Quantity(int total_Quantity) {
        Total_Quantity = total_Quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getSale_Price() {
        return Sale_Price;
    }

    public void setSale_Price(double sale_Price) {
        Sale_Price = sale_Price;
    }

    public double getDiscount() {
        return Discount;
    }

    public void setDiscount(double discount) {
        Discount = discount;
    }

    public double getShipping_Charge() {
        return Shipping_Charge;
    }

    public void setShipping_Charge(double shipping_Charge) {
        Shipping_Charge = shipping_Charge;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
