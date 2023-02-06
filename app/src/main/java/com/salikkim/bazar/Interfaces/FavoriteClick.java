package com.salikkim.bazar.Interfaces;

import com.salikkim.bazar.Models.Product;

public interface FavoriteClick {
    public void onRemoveClick(Product product, int position);
    public void onMoveClick(Product product, int position);
    public void onImageClick(Product product);
}
