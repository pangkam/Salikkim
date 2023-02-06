package com.salikkim.bazar.Adapters;

import android.content.Context;
import android.graphics.Color;
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
import com.salikkim.bazar.Models.Payment;
import com.salikkim.bazar.R;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Payment> paymentList;

    public PaymentAdapter(Context context, List<Payment> paymentList) {
        this.context = context;
        this.paymentList = paymentList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.card_payments, parent, false);
        return new PayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        PayViewHolder payViewHolder = (PayViewHolder) holder;
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
                load(paymentList.get(pos).getScreenshot())
                .placeholder(shimmerDrawable)
                .into(payViewHolder.screenshot);

        payViewHolder.id.setText(Html.fromHtml(paymentList.get(pos).getMode()+" ID: <b>"+paymentList.get(pos).getId()+"</b>"));
        payViewHolder.number.setText(Html.fromHtml(paymentList.get(pos).getMode()+" Number: <b>"+paymentList.get(pos).getNumber()+"</b>"));


    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    class PayViewHolder extends RecyclerView.ViewHolder {
        private TextView id, number;
        private ImageView screenshot;

        public PayViewHolder(@NonNull View itemView) {
            super(itemView);
            screenshot = itemView.findViewById(R.id.payment_screenshot);
            id = itemView.findViewById(R.id.payment_upi_id);
            number = itemView.findViewById(R.id.payment_upi_number);

        }
    }
}
