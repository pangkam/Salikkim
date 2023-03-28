package com.salikkim.bazar.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.salikkim.bazar.Activities.DetailActivity;
import com.salikkim.bazar.Adapters.ProductAdapter;
import com.salikkim.bazar.Helper.ApiController;
import com.salikkim.bazar.Interfaces.ProductClick;
import com.salikkim.bazar.Models.Product;
import com.salikkim.bazar.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ProductClick {
    private FragmentHomeBinding homeBinding;
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = homeBinding.getRoot();
        productAdapter = new ProductAdapter(getActivity(), productList, this);
        homeBinding.recViewHome.setHasFixedSize(true);
        homeBinding.recViewHome.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homeBinding.recViewHome.setAdapter(productAdapter);
        getProducts();
        return view;
    }

    private void getProducts() {
        productList = new ArrayList<>();
        Call<List<Product>> call = ApiController.getInstance().getApi().getProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body() != null) {
                    productList.addAll(response.body());
                    productAdapter.setProductsList(productList);
                }

            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onProductClick(Product product, int position) {
            startActivity(new Intent(getActivity(), DetailActivity.class)
                    .putExtra("product", product));
    }
}