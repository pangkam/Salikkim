package com.salikkim.bazar.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.salikkim.bazar.Adapters.ProductAdapter;
import com.salikkim.bazar.Helper.ApiController;
import com.salikkim.bazar.Interfaces.ProductClick;
import com.salikkim.bazar.Models.Product;
import com.salikkim.bazar.R;
import com.salikkim.bazar.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements ProductClick {
    private ActivitySearchBinding searchBinding;
    private ProductAdapter productAdapter;
    private List<Product> searchLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = searchBinding.getRoot();
        setContentView(view);
        String query = getIntent().getExtras().getString("query");
        searchBinding.toolbarSearchActivity.setTitle(query);
        setSupportActionBar(searchBinding.toolbarSearchActivity);
        searchBinding.toolbarSearchActivity.setNavigationIcon(R.drawable.baseline_arrow_back);
        searchBinding.toolbarSearchActivity.setNavigationOnClickListener(v -> finish());

        searchBinding.recViewSearch.setHasFixedSize(true);
        searchBinding.recViewSearch.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
        productAdapter = new ProductAdapter(SearchActivity.this, searchLists, this);
        searchBinding.recViewSearch.setAdapter(productAdapter);
        getSearchLists(query);
    }

    private void getSearchLists(String query) {
        Call<List<Product>> call = ApiController.getInstance().getApi().search("p.Title",query);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.body() != null) {
                    searchLists.addAll(response.body());
                    productAdapter.setProductsList(searchLists);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(SearchActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onProductClick(Product product, int position) {
        startActivity(new Intent(SearchActivity.this, DetailActivity.class)
                .putExtra("product", product));
    }
}