package com.salikkim.bazar.Adapters;

import android.content.Context;
import android.graphics.Color;
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
import com.salikkim.bazar.Interfaces.CategoryClick;
import com.salikkim.bazar.Models.Category;
import com.salikkim.bazar.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final Context context;
    private List<Category> categoryList;
    private CategoryClick categoryClick;

    public CategoryAdapter(Context context, List<Category> categoryList, CategoryClick categoryClick) {
        this.context = context;
        this.categoryList = categoryList;
        this.categoryClick = categoryClick;
    }

    public void setCategoriesList(List<Category> categoryList) {
        this.categoryList = categoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.card_category, parent, false);
        return new CategoryViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int pos = position;

        CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
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
                load(categoryList.get(pos).getThumbnail())
                .placeholder(shimmerDrawable)
                .into(categoryViewHolder.thumbnail);

        categoryViewHolder.title.setText(categoryList.get(pos).getTitle());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView thumbnail;
        private TextView title;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail_category);
            title = itemView.findViewById(R.id.title_category);
        }
    }
}
