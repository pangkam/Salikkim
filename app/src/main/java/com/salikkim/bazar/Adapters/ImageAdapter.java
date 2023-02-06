package com.salikkim.bazar.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;
import com.salikkim.bazar.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<String> imageLists;

    public ImageAdapter(Context context, List<String> imageLists) {
        this.context = context;
        this.imageLists = imageLists;
    }

    public void setImageLists(List<String> imageLists) {
        this.imageLists = imageLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.card_image, parent, false);
        return new ImageViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        final String thumbnail;
        if (imageLists.get(pos) != null) {
            thumbnail = imageLists.get(pos).replace("\"", "");
        } else {
            thumbnail = "";
        }
        ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
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
                .into(imageViewHolder.image);
    }

    @Override
    public int getItemCount() {
        return imageLists.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView_detail);

        }
    }
}
