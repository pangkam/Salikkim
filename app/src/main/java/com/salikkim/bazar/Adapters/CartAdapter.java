package com.salikkim.bazar.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.salikkim.bazar.Interfaces.CartClick;
import com.salikkim.bazar.Models.Address;
import com.salikkim.bazar.Models.Cart;
import com.salikkim.bazar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<Cart> cartList;
    private final CartClick cartClick;

    public CartAdapter(Context context,List<Cart> cartList, CartClick cartClick) {
        this.context = context;
        this.cartList = cartList;
        this.cartClick = cartClick;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.card_cart, parent, false);
        return new CartViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CartViewHolder cartViewHolder = (CartViewHolder) holder;
        Shimmer shimmer = new Shimmer.ColorHighlightBuilder()
                .setBaseColor(Color.parseColor("#F3F3F3"))
                .setBaseAlpha(1)
                .setHighlightColor(Color.parseColor("#E7E7E7"))
                .setHighlightAlpha(1)
                .setDropoff(50)
                .build();
        ShimmerDrawable shimmerDrawable = new ShimmerDrawable();
        String url = null;
        if (cartList.get(position).getThumbnail() != null) {
            url = cartList.get(position).getThumbnail().replace("\"", "");
        }
        shimmerDrawable.setShimmer(shimmer);
        Glide.with(context).
                load(url)
                .placeholder(shimmerDrawable)
                .into(cartViewHolder.thumbnail);
        cartViewHolder.title.setText(cartList.get(position).getTitle());
        cartViewHolder.seller.setText("Seller: " + cartList.get(position).getSeller_Name());
        cartViewHolder.price.setText(context.getString(R.string.Rs) + String.format("%.0f", cartList.get(position).getPrice()));
        cartViewHolder.price.setPaintFlags(cartViewHolder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        cartViewHolder.total.setText(context.getString(R.string.Rs) + String.format("%.0f", cartList.get(position).getSale_Price()));
        cartViewHolder.discount.setText(String.format("%.0f", cartList.get(position).getDiscount()) + "% Offer");
        cartViewHolder.color.setText("Color: " + cartList.get(position).getColor());
        cartViewHolder.size.setText("Size: " + cartList.get(position).getSize());
        cartViewHolder.quantity.setText("Qnty: " + cartList.get(position).getQuantity());

        final int quantity = cartList.get(position).getQuantity();
        final int total_quantity = cartList.get(position).getTotal_Quantity();

        if (!cartList.get(position).isAvailable()) {
            cartViewHolder.shipping.setVisibility(View.GONE);
            cartViewHolder.tv_not_deliver.setVisibility(View.VISIBLE);
            cartViewHolder.tv_not_deliver.setText(Html.fromHtml("<b>Sorry!</b> This item is not delivered to your address."));

        } else if (quantity > total_quantity) {
            cartViewHolder.shipping.setVisibility(View.GONE);
            cartViewHolder.tv_not_deliver.setVisibility(View.VISIBLE);
            cartViewHolder.tv_not_deliver.setText(Html.fromHtml("<b>Cannot proceed!</b> The available number of quantity is only " + total_quantity));

        } else if (!cartList.get(position).isAvailable() && quantity > total_quantity) {
            cartViewHolder.shipping.setVisibility(View.GONE);
            cartViewHolder.tv_not_deliver.setVisibility(View.VISIBLE);
            cartViewHolder.tv_not_deliver.setText(Html.fromHtml("<b>You can't buy this item at a time!</b>\nThe available number of quantity is only " + total_quantity + ". And not delivered to selected address"));

        } else {
            cartViewHolder.shipping.setVisibility(View.VISIBLE);
            cartViewHolder.tv_not_deliver.setVisibility(View.GONE);
            cartViewHolder.shipping.setText("Shipping charge: " + context.getString(R.string.Rs) + String.format("%.0f", cartList.get(position).getShipping_Charge()));
        }
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout pop_up;
        private ImageView thumbnail, btn_del;
        private TextView title, seller, price, total, color, size, quantity, discount, tv_not_deliver, shipping;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail_cart);
            btn_del = itemView.findViewById(R.id.btn_delete_cart);
            title = itemView.findViewById(R.id.cart_title);
            seller = itemView.findViewById(R.id.cart_seller_name);
            price = itemView.findViewById(R.id.cart_price);
            total = itemView.findViewById(R.id.cart_sale_price);
            color = itemView.findViewById(R.id.cart_color);
            size = itemView.findViewById(R.id.cart_size);
            quantity = itemView.findViewById(R.id.tv_qnty);
            discount = itemView.findViewById(R.id.cart_discount);
            tv_not_deliver = itemView.findViewById(R.id.tv_deliver_info_desc);
            pop_up = itemView.findViewById(R.id.select_qnty);
            shipping = itemView.findViewById(R.id.cart_shipping);

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartClick.onDeleteClick(cartList.get(getAdapterPosition()), getAdapterPosition());
                }
            });
            pop_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartClick.onQuantityClick(cartList.get(getAdapterPosition()), view);
                }
            });

        }
    }
}
