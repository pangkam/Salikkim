package com.salikkim.bazar.Models;

public class Address {
    private int Id;
    private String Name;

    public Address(int id, String name) {
        Id = id;
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public int getId() {
        return Id;
    }
}
