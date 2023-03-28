package com.salikkim.bazar.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.salikkim.bazar.Adapters.FavoriteAdapter;
import com.salikkim.bazar.Helper.ApiController;
import com.salikkim.bazar.Interfaces.FavoriteClick;
import com.salikkim.bazar.Models.Product;
import com.salikkim.bazar.Models.ResponseModel;
import com.salikkim.bazar.R;
import com.salikkim.bazar.databinding.ActivityFavoriteBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteActivity extends AppCompatActivity implements FavoriteClick {
    private ActivityFavoriteBinding favoriteBinding;
    private List<Product> favLists = new ArrayList<>();
    private FavoriteAdapter favoriteAdapter;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favoriteBinding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        View view = favoriteBinding.getRoot();
        setContentView(view);
        favoriteBinding.toolbarFavActivity.setTitle("Favorites");
        setSupportActionBar(favoriteBinding.toolbarFavActivity);
        favoriteBinding.toolbarFavActivity.setNavigationIcon(R.drawable.baseline_arrow_back);
        favoriteBinding.toolbarFavActivity.setNavigationOnClickListener(v -> finish());
        user_id = getIntent().getExtras().getString("user_id");
        favoriteBinding.recViewFav.setHasFixedSize(true);
        favoriteBinding.recViewFav.setLayoutManager(new GridLayoutManager(FavoriteActivity.this, 2));
        favoriteAdapter = new FavoriteAdapter(FavoriteActivity.this, favLists, this);
        favoriteBinding.recViewFav.setAdapter(favoriteAdapter);
        getFavLists();

    }

    private void getFavLists() {
        Call<List<Product>> call = ApiController.getInstance().getApi().getFavorite(user_id);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(FavoriteActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body() != null) {
                    favLists.addAll(response.body());
                    favoriteAdapter.setFavLists(favLists);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(FavoriteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    @Override
    public void onRemoveClick(Product product, int position) {
        Call<ResponseModel> call = ApiController.getInstance().getApi().deleteFav(product.getF_Id());
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        favLists.remove(position);
                        favoriteAdapter.notifyItemRemoved(position);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(FavoriteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

    @Override
    public void onMoveClick(Product product, int position) {
        Call<ResponseModel> call = ApiController.getInstance().getApi().moveCart(user_id,product.getF_Id(),product.getS_id(),product.getP_Id());
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        favLists.remove(position);
                        favoriteAdapter.notifyItemRemoved(position);
                    }else {
                        Toast.makeText(FavoriteActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(FavoriteActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onImageClick(Product product) {
        startActivity(new Intent(FavoriteActivity.this, DetailActivity.class)
                .putExtra("product", product));
    }
}