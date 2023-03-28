package com.salikkim.bazar.Interfaces;

import android.view.MenuItem;
import android.view.View;

import com.salikkim.bazar.Models.Cart;
import com.salikkim.bazar.Models.Order;

public interface OrderClick {
    public void onUploadClick(Order order);
    public void onOrderClick(Order order);
    public void onMenuClick(int position, Order cart, MenuItem menuItem);
}
