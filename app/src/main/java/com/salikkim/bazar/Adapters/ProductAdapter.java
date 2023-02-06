package com.salikkim.bazar.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.salikkim.bazar.Interfaces.ProductClick;
import com.salikkim.bazar.Models.Product;
import com.salikkim.bazar.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<Product> productList;
    private ProductClick productClick;

    public ProductAdapter(Context context, List<Product> productList, ProductClick productClick) {
        this.context = context;
        this.productList = productList;
        this.productClick = productClick;
    }

    public void setProductsList(List<Product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.card_product, parent, false);
        return new ProductViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        final String thumbnail;
        if (productList.get(pos).getThumbnail() != null) {
            thumbnail = productList.get(pos).getThumbnail().replace("\"", "");
        } else {
            thumbnail = "";
        }
        ProductViewHolder productViewHolder = (ProductViewHolder) holder;
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
                load(thumbnail)
                .placeholder(shimmerDrawable)
                .into(productViewHolder.thumbnail);

        productViewHolder.title.setText(productList.get(pos).getTitle());
        productViewHolder.price.setText(context.getString(R.string.Rs) + String.format("%.0f", productList.get(pos).getPrice()));
        productViewHolder.price.setPaintFlags(productViewHolder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        productViewHolder.sale_Price.setText(context.getString(R.string.Rs) + String.format("%.0f", productList.get(pos).getSale_Price()));
        productViewHolder.discount.setText(String.format("%.0f", productList.get(pos).getDiscount()) + "% Offer");
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView title, price, sale_Price, discount;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail_product);
            title = itemView.findViewById(R.id.main_title);
            price = itemView.findViewById(R.id.main_price);
            sale_Price = itemView.findViewById(R.id.main_sale_price);
            discount = itemView.findViewById(R.id.main_discount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    productClick.onProductClick(productList.get(getAdapterPosition()),getAdapterPosition());
                }
            });

        }
    }
}
