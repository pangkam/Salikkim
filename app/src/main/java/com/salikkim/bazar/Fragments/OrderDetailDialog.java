package com.salikkim.bazar.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.salikkim.bazar.Models.Order;
import com.salikkim.bazar.R;

public class OrderDetailDialog extends Dialog implements View.OnClickListener {
    private Activity activity;
    private final Order order;


    public OrderDetailDialog(@NonNull Activity activity, Order order) {
        super(activity);
        this.activity = activity;
        this.order = order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_order_detail);
        ImageView imageView = findViewById(R.id.detail_image);
        ImageView btn_close = findViewById(R.id.order_detail_btn_close);
        TextView address = findViewById(R.id.detail_address);

        TextView title = findViewById(R.id.detail_title);
        TextView price = findViewById(R.id.detail_price);
        TextView sale_price = findViewById(R.id.detail_sale_price);
        TextView discount = findViewById(R.id.detail_discount);
        TextView color = findViewById(R.id.detail_color);
        TextView size = findViewById(R.id.detail_size);
        TextView quantity = findViewById(R.id.detail_quantity);
        TextView seller = findViewById(R.id.detail_seller);
        TextView status = findViewById(R.id.detail_status);
        TextView total_price = findViewById(R.id.detail_total_price);
        TextView final_price = findViewById(R.id.detail_final_price);

        btn_close.setOnClickListener(this);


        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#F3F3F3"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#E7E7E7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);
        Glide.with(activity).
                load(order.getThumbnail())
                .placeholder(shimmerDrawable)
                .into(imageView);


        title.setText(order.getTitle());
        price.setText(activity.getString(R.string.Rs) + String.format("%.0f", order.getPrice()));
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        sale_price.setText(activity.getString(R.string.Rs) + String.format("%.0f", order.getSale_Price()));
        discount.setText(String.format("%.0f", order.getDiscount()) + "% Offer");
        color.setText(Html.fromHtml("Color: <b>" + order.getColor() + "</b>"));
        size.setText(Html.fromHtml("Size: <b>" + order.getSize() + "</b>"));
        quantity.setText(Html.fromHtml("Quantity: <b>" + order.getQuantity() + "</b>"));
        seller.setText(Html.fromHtml("Seller: <b>" + order.getSeller_Name() + "</b>"));
        status.setText(Html.fromHtml("Status: <b>" + order.getStatus() + "</b>"));
        total_price.setText(Html.fromHtml("<b>" + activity.getString(R.string.Rs) + String.format("%.0f", order.getSale_Price()) + " + " + activity.getString(R.string.Rs) + String.format("%.0f", order.getShipping_Charge()) + "</b>(Shipping Charge) <b>x</b> " + order.getQuantity()));

        final_price.setText(Html.fromHtml("Total: <b>" + activity.getString(R.string.Rs) + String.format("%.0f", ((order.getSale_Price()) + order.getShipping_Charge()) * order.getQuantity()) + "</b>"));

        address.setText(Html.fromHtml("Address: <b>" + order.getAddress() + "</b>"));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_detail_btn_close:
                dismiss();
                break;
        }
    }

    private void gotoUpload() {
    }
}
