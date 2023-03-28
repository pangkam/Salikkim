package com.salikkim.bazar.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private final OrderClick orderClick;

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
        if (orderList.get(pos).getPayment_Screenshot() != null) {
            if (orderList.get(pos).getPayment_Screenshot().isEmpty()) {
                orderViewHolder.upload.setVisibility(View.VISIBLE);
            }
        }else {
            orderViewHolder.upload.setVisibility(View.INVISIBLE);
        }

     /*   orderViewHolder.seller.setText(Html.fromHtml("Seller: <b>" + orderList.get(pos).getSeller_Name() + "</b>"));
        orderViewHolder.price.setText(Html.fromHtml("<b>" + context.getString(R.string.Rs) +
                String.format("%.0f", orderList.get(pos).getSale_Price())
                +
                " + " + context.getString(R.string.Rs) + String.format("%.0f", orderList.get(pos).getShipping_Charge()) + "</b>(Shipping Charge) <b>x</b> " + orderList.get(pos).getQuantity()));

        orderViewHolder.total_price.setText(Html.fromHtml("Total: <b>" + context.getString(R.string.Rs) + String.format("%.0f", ((orderList.get(pos).getSale_Price())
                + orderList.get(pos).getShipping_Charge()) * orderList.get(pos).getQuantity()) + "</b>"));
*/
        orderViewHolder.color.setText(Html.fromHtml("Color: <b>" + orderList.get(pos).getColor() + "</b>"));
        orderViewHolder.size.setText(Html.fromHtml("Size: <b>" + orderList.get(pos).getSize() + "</b>"));
        orderViewHolder.quantity.setText(Html.fromHtml("Quantity: <b>" + orderList.get(pos).getQuantity() + "</b>"));
        orderViewHolder.status.setText(Html.fromHtml("<b>" + orderList.get(pos).getStatus() + "</b>"));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        private ImageView thumbnail, btn_more;
        private TextView title, color, size, quantity, status, upload;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.order_thumbnail);
            title = itemView.findViewById(R.id.order_title);
            color = itemView.findViewById(R.id.order_color);
            size = itemView.findViewById(R.id.order_size);
            quantity = itemView.findViewById(R.id.order_quantity);
            status = itemView.findViewById(R.id.order_status);
            btn_more = itemView.findViewById(R.id.order_btn_more);
            upload = itemView.findViewById(R.id.order_btn_upload);

            itemView.setOnClickListener(this);
            upload.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    orderClick.onOrderClick(orderList.get(getAdapterPosition()));
                }
            });

            btn_more.setOnClickListener(this);
        }

        @SuppressLint("RestrictedApi")
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.order_btn_more:
                    PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
                    if (popupMenu.getMenu() instanceof MenuBuilder) {
                        ((MenuBuilder) popupMenu.getMenu()).setOptionalIconsVisible(true);
                    }
                    popupMenu.getMenuInflater().inflate(R.menu.menu_order, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(this);
                    popupMenu.show();
                    break;
                case R.id.order_btn_upload:
                    orderClick.onUploadClick(orderList.get(getAdapterPosition()));
                    break;
            }

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            int position = getAdapterPosition();
            orderClick.onMenuClick(position, orderList.get(position), menuItem);
            return false;
        }
    }
}
