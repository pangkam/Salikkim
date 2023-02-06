package com.salikkim.bazar.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.salikkim.bazar.Interfaces.OrderClick;
import com.salikkim.bazar.Models.Order;
import com.salikkim.bazar.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<Order> orderList;
    private OrderClick orderClick;

    public OrderAdapter(Context context, List<Order> orderList, OrderClick orderClick) {
        this.context = context;
        this.orderList = orderList;
        this.orderClick = orderClick;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.card_order, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        OrderViewHolder orderViewHolder = (OrderViewHolder) holder;
        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#F3F3F3"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#E7E7E7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        shimmerDrawable.setShimmer(shimmer);
        Glide.with(context).
                load(orderList.get(pos).getThumbnail().replace("\"", ""))
                .placeholder(shimmerDrawable)
                .into(orderViewHolder.thumbnail);
        orderViewHolder.title.setText(orderList.get(pos).getTitle());
        orderViewHolder.seller.setText(Html.fromHtml("Seller: <b>" + orderList.get(pos).getS_Name() + "</b>"));
        orderViewHolder.total.setText(Html.fromHtml("Price: <b>" + context.getString(R.string.Rs) + String.format("%.0f", orderList.get(pos).getTotal_Price()) + "</b>"));
        orderViewHolder.shipping.setText(Html.fromHtml("Shipping charge: <b>" + context.getString(R.string.Rs) + String.format("%.0f", orderList.get(pos).getShipping_Charge()) + "</b>"));
        orderViewHolder.color.setText(Html.fromHtml("Color: <b>" + orderList.get(pos).getColor() + "</b>"));
        orderViewHolder.size.setText(Html.fromHtml("Size: <b>" + orderList.get(pos).getSize() + "</b>"));
        orderViewHolder.qnty.setText(Html.fromHtml("Quantity: <b>" + orderList.get(pos).getQuantity() + "</b>"));
        orderViewHolder.address.setText(Html.fromHtml("<b>" + orderList.get(pos).getAddress() + "</b>"));
        orderViewHolder.status.setText(Html.fromHtml("<b>" + orderList.get(pos).getStatus() + "</b>"));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private ImageView thumbnail, btn_more;
        private TextView title, seller, total, shipping, color, size, qnty, address, status, action_btn;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.order_thumbnail);
            title = itemView.findViewById(R.id.order_title);
            seller = itemView.findViewById(R.id.order_seller_name);
            total = itemView.findViewById(R.id.order_price);
            shipping = itemView.findViewById(R.id.order_shipping_charge);
            color = itemView.findViewById(R.id.order_color);
            size = itemView.findViewById(R.id.order_size);
            qnty = itemView.findViewById(R.id.order_quantity);
            address = itemView.findViewById(R.id.order_address);
            status = itemView.findViewById(R.id.order_status);
            action_btn = itemView.findViewById(R.id.order_btn_action);
            btn_more = itemView.findViewById(R.id.order_btn_more);


            action_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderClick.onActionClick(orderList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
            btn_more.setOnClickListener(this);
        }

        @SuppressLint("RestrictedApi")
        @Override
        public void onClick(View view) {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            if (popupMenu.getMenu() instanceof MenuBuilder) {
                ((MenuBuilder) popupMenu.getMenu()).setOptionalIconsVisible(true);
            }
            popupMenu.getMenuInflater().inflate(R.menu.menu_order, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int position = getAdapterPosition();
            orderClick.onMenuClick(position,orderList.get(position),menuItem);
            return false;
        }
    }
}
