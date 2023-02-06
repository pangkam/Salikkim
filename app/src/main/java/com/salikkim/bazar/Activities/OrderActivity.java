package com.salikkim.bazar.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.salikkim.bazar.Adapters.OrderAdapter;
import com.salikkim.bazar.Helper.ApiController;
import com.salikkim.bazar.Interfaces.OrderClick;
import com.salikkim.bazar.Models.Order;
import com.salikkim.bazar.R;
import com.salikkim.bazar.databinding.ActivityOrderBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity implements OrderClick {
    private ActivityOrderBinding orderBinding;
    private List<Order> orderList = new ArrayList<>();
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderBinding = ActivityOrderBinding.inflate(getLayoutInflater());
        View view = orderBinding.getRoot();
        setContentView(view);
        orderBinding.toolbarOrderActivity.setTitle("Cart");
        setSupportActionBar(orderBinding.toolbarOrderActivity);
        orderBinding.toolbarOrderActivity.setNavigationIcon(R.drawable.baseline_arrow_back);
        orderBinding.toolbarOrderActivity.setNavigationOnClickListener(v -> finish());
        orderBinding.recViewOrder.setHasFixedSize(true);
        orderBinding.recViewOrder.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
        orderAdapter = new OrderAdapter(OrderActivity.this, orderList, this);
        orderBinding.recViewOrder.setAdapter(orderAdapter);
        getOrderLists();
    }

    private void getOrderLists() {
        Call<List<Order>> call = ApiController.getInstance().getApi().getOrder(1);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.body() != null) {
                    orderList.addAll(response.body());
                    orderAdapter.setOrderList(orderList);
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(OrderActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActionClick(Order cart, int position) {

    }

    @Override
    public void onMenuClick(int position, Order cart, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.order_chat:
                Toast.makeText(this, "Chat with seller", Toast.LENGTH_SHORT).show();
                break;
            case R.id.order_report:
                Toast.makeText(this, "Report", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}