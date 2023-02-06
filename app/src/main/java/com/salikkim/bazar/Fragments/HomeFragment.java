package com.salikkim.bazar.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements ProductClick {
    private FragmentHomeBinding homeBinding;
    private List<Product> productList = new ArrayList<>();
    private ProductAdapter productAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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