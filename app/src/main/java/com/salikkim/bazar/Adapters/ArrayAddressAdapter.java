package com.salikkim.bazar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.salikkim.bazar.Models.Address;
import com.salikkim.bazar.R;

import java.util.List;


public class ArrayAddressAdapter extends ArrayAdapter<Address> {

    public ArrayAddressAdapter(Context context, List<Address> addressesLists) {
        super(context, 0, addressesLists);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position,convertView,parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.card_address2, parent, false
            );
        }

        TextView tv_address = convertView.findViewById(R.id.tv_address_detail);
        Address currentItem = getItem(position);
        if (currentItem != null) {
            tv_address.setText(currentItem.getName());
        }
        return convertView;
    }
}
