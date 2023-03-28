package com.salikkim.bazar.Models;

public class Address {
    private int Id;
    private String Name;
    private String Shipping;

    public Address(int id, String name, String shipping) {
        Id = id;
        Name = name;
        Shipping = shipping;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getShipping() {
        return Shipping;
    }
}
