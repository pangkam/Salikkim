package com.salikkim.bazar.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Html;
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
import com.salikkim.bazar.Interfaces.FavoriteClick;
import com.salikkim.bazar.Models.Product;
import com.salikkim.bazar.R;


import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Product> favLists;

    private FavoriteClick favoriteClick;

    public FavoriteAdapter(Context context, List<Product> favLists, FavoriteClick favoriteClick) {
        this.context = context;
        this.favLists = favLists;
        this.favoriteClick = favoriteClick;
    }

    public void setFavLists(List<Product> favLists) {
        this.favLists = favLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.card_favorite, parent, false);
        return new FavViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        FavViewHolder favViewHolder = (FavViewHolder) holder;
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
                load(favLists.get(pos).getThumbnail().replace("\"", ""))
                .placeholder(shimmerDrawable)
                .into(favViewHolder.thumbnail);
        favViewHolder.title.setText(favLists.get(pos).getTitle());
        favViewHolder.seller.setText(Html.fromHtml("Seller: <b>" + favLists.get(pos).getSeller_Name() + "</b>"));
        favViewHolder.color.setText(Html.fromHtml("Color: " + favLists.get(pos).getColor()));
        favViewHolder.size.setText(Html.fromHtml("Size: " + favLists.get(pos).getSize()));
        favViewHolder.price.setText(context.getString(R.string.Rs) + String.format("%.0f", favLists.get(pos).getPrice()));
        favViewHolder.price.setPaintFlags(favViewHolder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        favViewHolder.sale_price.setText(context.getString(R.string.Rs) + String.format("%.0f", favLists.get(pos).getSale_Price()));
        favViewHolder.discounts.setText(String.format("%.0f", favLists.get(pos).getDiscount()) + "% Offer");
    }

    @Override
    public int getItemCount() {
        return favLists.size();
    }

    class FavViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail, btn_del;
        private TextView title, seller, price, sale_price, discounts, color, size, btn_move;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail_fav);
            title = itemView.findViewById(R.id.title_fav);
            seller = itemView.findViewById(R.id.sellerName_fav);
            color = itemView.findViewById(R.id.color_fav);
            size = itemView.findViewById(R.id.size_fav);
            price = itemView.findViewById(R.id.price_fav);
            sale_price = itemView.findViewById(R.id.sale_price_fav);
            discounts = itemView.findViewById(R.id.discount_fav);
            btn_del = itemView.findViewById(R.id.btn_remove_fav);
            btn_move = itemView.findViewById(R.id.btn_move_fav);

            btn_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteClick.onRemoveClick(favLists.get(getAdapterPosition()), getAdapterPosition());
                }
            });

            btn_move.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteClick.onMoveClick(favLists.get(getAdapterPosition()), getAdapterPosition());
                }
            });

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteClick.onImageClick(favLists.get(getAdapterPosition()));
                }
            });
        }
    }
}
