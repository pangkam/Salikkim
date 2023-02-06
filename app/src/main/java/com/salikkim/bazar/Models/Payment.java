package com.salikkim.bazar.Models;

public class Payment {
    private String screenshot,id,number,mode;

    public Payment(String screenshot, String id, String number, String mode) {
        this.screenshot = screenshot;
        this.id = id;
        this.number = number;
        this.mode = mode;
    }

    public String getScreenshot() {
        return screenshot;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getMode() {
        return mode;
    }
}
