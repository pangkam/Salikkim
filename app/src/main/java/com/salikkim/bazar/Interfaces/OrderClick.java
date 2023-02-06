package com.salikkim.bazar.Interfaces;

import android.view.MenuItem;
import android.view.View;

import com.salikkim.bazar.Models.Cart;
import com.salikkim.bazar.Models.Order;

public interface OrderClick {
    public void onActionClick(Order cart, int position);
    public void onMenuClick(int position, Order cart, MenuItem menuItem);
}
