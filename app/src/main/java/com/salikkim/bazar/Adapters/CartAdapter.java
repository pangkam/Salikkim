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
import com.salikkim.bazar.Models.Cart;
import com.salikkim.bazar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private List<Cart> cartList;
    private String address_name;
    private CartClick cartClick;

    public CartAdapter(Context context, List<Cart> cartList, String address_name, CartClick cartClick) {
        this.context = context;
        this.cartList = cartList;
        this.address_name = address_name;
        this.cartClick = cartClick;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
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
        final int pos = position;
        CartViewHolder cartViewHolder = (CartViewHolder) holder;
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
                load(cartList.get(pos).getThumbnail().replace("\"", ""))
                .placeholder(shimmerDrawable)
                .into(cartViewHolder.thumbnail);
        cartViewHolder.title.setText(cartList.get(pos).getTitle());
        cartViewHolder.seller.setText("Seller: " + cartList.get(pos).getSeller_Name());
        cartViewHolder.price.setText(context.getString(R.string.Rs) + String.format("%.0f", cartList.get(pos).getPrice()));
        cartViewHolder.price.setPaintFlags(cartViewHolder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        cartViewHolder.total.setText(context.getString(R.string.Rs) + String.format("%.0f", cartList.get(pos).getSale_Price()));
        cartViewHolder.discount.setText(String.format("%.0f", cartList.get(pos).getDiscount()) + "% Offer");
        cartViewHolder.color.setText("Color: " + cartList.get(pos).getColor());
        cartViewHolder.size.setText("Size: " + cartList.get(pos).getSize());
        cartViewHolder.qnty.setText("Qnty: " + cartList.get(pos).getQuantity());
        if (cartList.get(pos).getCOD().equals("Yes")) {
            cartViewHolder.cod.setVisibility(View.VISIBLE);
        }

        final int qnty = cartList.get(pos).getQuantity();
        final int total_qnty = cartList.get(pos).getTotal_Quantity();
        if (cartList.get(pos).getAddresses().contains(address_name)) {
            cartViewHolder.tv_not_deliver.setVisibility(View.GONE);
            try {
                setShippingCharge(cartViewHolder, cartList.get(pos).getAddresses());
                if(qnty>total_qnty){
                    cartViewHolder.tv_not_deliver.setVisibility(View.VISIBLE);
                    cartViewHolder.tv_not_deliver.setText(Html.fromHtml("<b>You can't buy this item at a time!</b>\nThe available number of quantity is only " + total_qnty+", please set the quantity to less than "+total_qnty+" or equal to "+total_qnty));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            cartViewHolder.shipping.setVisibility(View.GONE);
            cartViewHolder.tv_not_deliver.setVisibility(View.VISIBLE);
            cartViewHolder.tv_not_deliver.setText("Not delivered to " + address_name);
            if(qnty>total_qnty){
                cartViewHolder.tv_not_deliver.setText(Html.fromHtml("<b>You can't buy this item at a time!</b>\nThe available number of quantity is only " + total_qnty+". And not delivered to "+address_name));
            }
        }
    }

    private void setShippingCharge(CartViewHolder cartViewHolder, String addresses) throws JSONException {
        JSONArray jsonArray = new JSONArray(addresses);
        for (int j = 0; j < jsonArray.length(); j++) {
            JSONObject obj = jsonArray.getJSONObject(j);
            int id = obj.getInt("Id");
            String name = obj.getString("Name");
            double charge = obj.getDouble("Charge");
            if (name.equals(address_name)) {
                cartViewHolder.shipping.setVisibility(View.VISIBLE);
                cartViewHolder.shipping.setText("Shipping charge: " + context.getString(R.string.Rs) + String.format("%.0f", charge));
            }
        }
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout pop_up;
        private ImageView thumbnail, btn_del;
        private TextView title, seller, price, total, color, size, qnty, discount, tv_not_deliver, cod, shipping;

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
            qnty = itemView.findViewById(R.id.tv_qnty);
            discount = itemView.findViewById(R.id.cart_discount);
            tv_not_deliver = itemView.findViewById(R.id.tv_deliver_info_desc);
            pop_up = itemView.findViewById(R.id.select_qnty);
            cod = itemView.findViewById(R.id.cart_cod);
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
