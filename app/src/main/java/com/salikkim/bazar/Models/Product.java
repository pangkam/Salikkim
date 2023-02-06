package com.salikkim.bazar.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {
    private String Category,Images,Thumbnail,Title,Color,Size,P_Desc,COD,Addresses,Seller_Name;
    private int F_Id,P_Id,S_id,Quantity;
    private double Price,Sale_Price,Discount;

    protected Product(Parcel in) {
        Category = in.readString();
        Images = in.readString();
        Thumbnail = in.readString();
        Title = in.readString();
        Color = in.readString();
        Size = in.readString();
        P_Desc = in.readString();
        COD = in.readString();
        Addresses = in.readString();
        Seller_Name = in.readString();
        F_Id = in.readInt();
        P_Id = in.readInt();
        S_id = in.readInt();
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
        dest.writeString(COD);
        dest.writeString(Addresses);
        dest.writeString(Seller_Name);
        dest.writeInt(F_Id);
        dest.writeInt(P_Id);
        dest.writeInt(S_id);
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

    public String getP_Desc() {
        return P_Desc;
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

    public int getF_Id() {
        return F_Id;
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
