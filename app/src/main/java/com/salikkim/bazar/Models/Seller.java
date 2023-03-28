package com.salikkim.bazar.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Seller implements Parcelable {
    private String S_Id,Name,Address,Mobile,Alt_Mobile,Email,Accounts;

    public Seller() {
    }

    protected Seller(Parcel in) {
        S_Id = in.readString();
        Name = in.readString();
        Address = in.readString();
        Mobile = in.readString();
        Alt_Mobile = in.readString();
        Email = in.readString();
        Accounts = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(S_Id);
        dest.writeString(Name);
        dest.writeString(Address);
        dest.writeString(Mobile);
        dest.writeString(Alt_Mobile);
        dest.writeString(Email);
        dest.writeString(Accounts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Seller> CREATOR = new Creator<Seller>() {
        @Override
        public Seller createFromParcel(Parcel in) {
            return new Seller(in);
        }

        @Override
        public Seller[] newArray(int size) {
            return new Seller[size];
        }
    };

    public String getS_Id() {
        return S_Id;
    }

    public void setS_Id(String s_Id) {
        S_Id = s_Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAlt_Mobile() {
        return Alt_Mobile;
    }

    public void setAlt_Mobile(String alt_Mobile) {
        Alt_Mobile = alt_Mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAccounts() {
        return Accounts;
    }

    public void setAccounts(String accounts) {
        Accounts = accounts;
    }
}
