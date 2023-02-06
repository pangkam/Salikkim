package com.salikkim.bazar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salikkim.bazar.R;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> addressLists;

    public AddressAdapter(Context context, List<String> addressLists) {
        this.context = context;
        this.addressLists = addressLists;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(
                R.layout.card_address, parent, false);
        return new AddressViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        AddressViewHolder addressViewHolder = (AddressViewHolder) holder;
        addressViewHolder.addressView.setText(addressLists.get(pos));


    }

    @Override
    public int getItemCount() {
        return addressLists.size();
    }

    class AddressViewHolder extends RecyclerView.ViewHolder {
        private TextView addressView;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            addressView = itemView.findViewById(R.id.tv_address_detail);

        }
    }
}
