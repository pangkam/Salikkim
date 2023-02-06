package com.salikkim.bazar.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.salikkim.bazar.Adapters.AddressAdapter;
import com.salikkim.bazar.Adapters.ImageAdapter;
import com.salikkim.bazar.Helper.ApiController;
import com.salikkim.bazar.Models.Address;
import com.salikkim.bazar.Models.Product;
import com.salikkim.bazar.Models.ResponseModel;
import com.salikkim.bazar.R;
import com.salikkim.bazar.databinding.ActivityDetailBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {
    private ActivityDetailBinding detailBinding;
    private Product product;
    private ArrayList<String> imageLists = new ArrayList<>();
    private ArrayList<Address> addressLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        View view = detailBinding.getRoot();
        setContentView(view);
        product = (Product) getIntent().getParcelableExtra("product");
        if (product != null)
            initViews();
    }

    private void initViews() {
        try {
            setImages();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        detailBinding.titleDetail.setText(product.getTitle());
        detailBinding.priceDetail.setText(getString(R.string.Rs) + String.format("%.0f", product.getPrice()));
        detailBinding.priceDetail.setPaintFlags(detailBinding.priceDetail.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        detailBinding.offerDetail.setText(String.format("%.0f", product.getDiscount()) + "% Offer");
        detailBinding.salePriceDetail.setText(getString(R.string.Rs) + String.format("%.0f", product.getSale_Price()));
        detailBinding.colorDetail.setText(Html.fromHtml("Color: <b>" + product.getColor() + "</b>"));
        detailBinding.sizeDetail.setText(Html.fromHtml("Size: <b>" + product.getSize() + "</b>"));
        detailBinding.descDetail.setText(product.getP_Desc());
        detailBinding.sellerDetail.setText(Html.fromHtml("Seller: <b>" + product.getSeller_Name() + "</b>"));
        detailBinding.quantityDetail.setText(product.getQuantity()+" lefts");
        if (product.getCOD().equals("Yes")) {
            detailBinding.codDetail.setVisibility(View.VISIBLE);
        } else {
            detailBinding.codDetail.setVisibility(View.GONE);
        }

        if (product.getQuantity() >= 0) {
            detailBinding.tvStockDetail.setVisibility(View.GONE);
            detailBinding.btnAddFavorite.setVisibility(View.VISIBLE);
            detailBinding.btnAddCart.setVisibility(View.VISIBLE);
        } else {
            detailBinding.tvStockDetail.setVisibility(View.VISIBLE);
            detailBinding.btnAddFavorite.setVisibility(View.GONE);
            detailBinding.btnAddCart.setVisibility(View.GONE);
        }

        try {
            setAddress();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        detailBinding.btnCloseDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        detailBinding.btnAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detailBinding.progressFavorite.getVisibility() != View.VISIBLE & detailBinding.progressCart.getVisibility() != View.VISIBLE) {
                    addToFavorite();
                } else {
                    Toast.makeText(DetailActivity.this, "Please wait", Toast.LENGTH_SHORT).show();
                }
            }
        });

        detailBinding.btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (detailBinding.progressFavorite.getVisibility() != View.VISIBLE & detailBinding.progressCart.getVisibility() != View.VISIBLE) {
                    if (detailBinding.tvCartDetail.getText().toString().equals("GO TO CART")) {
                        startActivity(new Intent(DetailActivity.this, CartActivity.class));
                    } else {
                        addToCart();
                    }
                } else {
                    Toast.makeText(DetailActivity.this, "Please wait", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void addToFavorite() {
        detailBinding.tvFavoriteDetail.setVisibility(View.GONE);
        detailBinding.progressFavorite.setVisibility(View.VISIBLE);
        Call<ResponseModel> call = ApiController.getInstance().getApi().addFavorite(1, product.getP_Id());
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DetailActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
                if (response.body() != null) {
                    Toast.makeText(DetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
                detailBinding.tvFavoriteDetail.setVisibility(View.VISIBLE);
                detailBinding.progressFavorite.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void addToCart() {
        detailBinding.tvCartDetail.setVisibility(View.GONE);
        detailBinding.progressCart.setVisibility(View.VISIBLE);
        Call<ResponseModel> call = ApiController.getInstance().getApi().addToCart(1,product.getS_id(),product.getP_Id());
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DetailActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
                if (response.body() != null) {
                    Toast.makeText(DetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if(response.body().getStatus()){
                        detailBinding.tvCartDetail.setText("GO TO CART");
                    }
                }
                detailBinding.tvCartDetail.setVisibility(View.VISIBLE);
                detailBinding.progressCart.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setImages() throws JSONException {
        imageLists = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(product.getImages());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String uri = obj.getString("Img");
            imageLists.add(uri);

        }
        ImageAdapter imageAdapter = new ImageAdapter(DetailActivity.this, imageLists);
        detailBinding.viewPagerDetail.setAdapter(imageAdapter);
        detailBinding.wormDotsIndicator.setViewPager2(detailBinding.viewPagerDetail);

    }

    private void setAddress() throws JSONException {
        addressLists = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(product.getAddresses());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String address = i + 1 + "." + obj.getString("Name");
            int id = obj.getInt("Id");
            addressLists.add(new Address(id,address));

        }
        detailBinding.recViewAddressDetail.setHasFixedSize(false);
        detailBinding.recViewAddressDetail.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        AddressAdapter addressAdapter = new AddressAdapter(DetailActivity.this, addressLists);
        detailBinding.recViewAddressDetail.setAdapter(addressAdapter);
    }

}