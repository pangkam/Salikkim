package com.salikkim.bazar.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.salikkim.bazar.Adapters.OrderAdapter;
import com.salikkim.bazar.Fragments.OrderDetailDialog;
import com.salikkim.bazar.Fragments.SetupAcDialog;
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
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderBinding = ActivityOrderBinding.inflate(getLayoutInflater());
        View view = orderBinding.getRoot();
        setContentView(view);
        orderBinding.toolbarOrderActivity.setTitle("Orders");
        setSupportActionBar(orderBinding.toolbarOrderActivity);
        orderBinding.toolbarOrderActivity.setNavigationIcon(R.drawable.baseline_arrow_back);
        orderBinding.toolbarOrderActivity.setNavigationOnClickListener(v -> finish());
        user_id = getIntent().getExtras().getString("user_id");
        orderBinding.recViewOrder.setHasFixedSize(true);
        orderBinding.recViewOrder.setLayoutManager(new LinearLayoutManager(OrderActivity.this));
        orderAdapter = new OrderAdapter(OrderActivity.this, orderList, this);
        orderBinding.recViewOrder.setAdapter(orderAdapter);
        getOrderLists();
    }

    private void getOrderLists() {
        Call<List<Order>> call = ApiController.getInstance().getApi().getOrder(user_id);
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
                Log.d("myTag", t.getMessage());
            }
        });
    }

    @Override
    public void onUploadClick(Order order) {
        Toast.makeText(this, ""+order.getPayment_Screenshot(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onOrderClick(Order order) {
        OrderDetailDialog orderDetailDialog = new OrderDetailDialog(OrderActivity.this, order);
        orderDetailDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        orderDetailDialog.setCancelable(true);
        orderDetailDialog.show();
        Window window = orderDetailDialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onMenuClick(int position, Order cart, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.order_chat:
                Toast.makeText(this, "Chat with seller", Toast.LENGTH_SHORT).show();
                break;
            case R.id.order_screenshot:
                Toast.makeText(this, "Screenshot", Toast.LENGTH_SHORT).show();
                break;
            case R.id.order_report:
                Toast.makeText(this, "Report", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}