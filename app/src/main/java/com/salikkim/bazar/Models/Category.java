package com.salikkim.bazar.Models;

public class Category {
    private String Thumbnail,Title;
    private int C_Id;

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

    public int getC_Id() {
        return C_Id;
    }

    public void setC_Id(int c_Id) {
        C_Id = c_Id;
    }
}
