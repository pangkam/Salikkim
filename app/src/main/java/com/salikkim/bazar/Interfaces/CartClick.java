package com.salikkim.bazar.Interfaces;

import android.view.View;

import com.salikkim.bazar.Models.Cart;
import com.salikkim.bazar.Models.Product;

public interface CartClick {
    public void onQuantityClick(Cart cart, View view);
    public void onDeleteClick(Cart cart, int position);
}
