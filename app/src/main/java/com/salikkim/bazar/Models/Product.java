package com.salikkim.bazar.Models;

import android.os.Parcel;
import android.os.Parcelable;


public class Product implements Parcelable {
    private String Category;
    private String Images;
    private String Thumbnail;
    private String Title;
    private String Color;
    private String Size;
    private String P_Desc;
    private  String Address;
    private String Seller_Name;
    private String S_id;
    private int F_Id, P_Id, Quantity;
    private double Price, Sale_Price, Discount;

    protected Product(Parcel in) {
        Category = in.readString();
        Images = in.readString();
        Thumbnail = in.readString();
        Title = in.readString();
        Color = in.readString();
        Size = in.readString();
        P_Desc = in.readString();
        Address = in.readString();
        Seller_Name = in.readString();
        S_id = in.readString();
        F_Id = in.readInt();
        P_Id = in.readInt();
        Quantity = in.readInt();
        Price = in.readDouble();
        Sale_Price = in.readDouble();
        Discount = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Category);
        dest.writeString(Images);
        dest.writeString(Thumbnail);
        dest.writeString(Title);
        dest.writeString(Color);
        dest.writeString(Size);
        dest.writeString(P_Desc);
        dest.writeString(Address);
        dest.writeString(Seller_Name);
        dest.writeString(S_id);
        dest.writeInt(F_Id);
        dest.writeInt(P_Id);
        dest.writeInt(Quantity);
        dest.writeDouble(Price);
        dest.writeDouble(Sale_Price);
        dest.writeDouble(Discount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

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

    public String getP_Desc() {
        return P_Desc;
    }

    public void setP_Desc(String p_Desc) {
        P_Desc = p_Desc;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSeller_Name() {
        return Seller_Name;
    }

    public void setSeller_Name(String seller_Name) {
        Seller_Name = seller_Name;
    }

    public String getS_id() {
        return S_id;
    }

    public void setS_id(String s_id) {
        S_id = s_id;
    }

    public int getF_Id() {
        return F_Id;
    }

    public void setF_Id(int f_Id) {
        F_Id = f_Id;
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

}
