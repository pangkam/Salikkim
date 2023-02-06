package com.salikkim.bazar.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.salikkim.bazar.Adapters.ArrayAddressAdapter;
import com.salikkim.bazar.Adapters.CartAdapter;
import com.salikkim.bazar.Helper.ApiController;
import com.salikkim.bazar.Interfaces.CartClick;
import com.salikkim.bazar.Models.Address;
import com.salikkim.bazar.Models.Cart;
import com.salikkim.bazar.Models.ResponseModel;
import com.salikkim.bazar.R;
import com.salikkim.bazar.databinding.ActivityCartBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements CartClick, AdapterView.OnItemSelectedListener, View.OnClickListener {
    private ActivityCartBinding cartBinding;
    private List<Cart> cartList = new ArrayList<>();
    private CartAdapter cartAdapter;
    private String address_name = "Ampati, South West Garo Hills, Meghalaya";
    private int address_id = 0;
    private double total_price = 0;
    private int seller_id = 0;
    private String seller_contacts = "";
    private String seller_accounts = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cartBinding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = cartBinding.getRoot();
        setContentView(view);
        cartBinding.toolbarCartActivity.setTitle("Cart");
        setSupportActionBar(cartBinding.toolbarCartActivity);
        cartBinding.toolbarCartActivity.setNavigationIcon(R.drawable.baseline_arrow_back);
        cartBinding.toolbarCartActivity.setNavigationOnClickListener(v -> finish());
        cartBinding.recViewCart.setHasFixedSize(false);
        cartBinding.recViewCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        cartAdapter = new CartAdapter(CartActivity.this, cartList, address_name, this);
        cartBinding.recViewCart.setAdapter(cartAdapter);
        cartBinding.cartAddrSpinner.setOnItemSelectedListener(this);
        getAddress(address_name);
        getCartLists();
        cartBinding.btnCheckoutCart.setOnClickListener(this);
    }

    private void getAddress(String ad_name) {
        Call<List<Address>> call = ApiController.getInstance().getApi().getAddress();
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(CartActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body() != null) {
                    ArrayAddressAdapter addressAdapter = new ArrayAddressAdapter(CartActivity.this, response.body());
                    cartBinding.cartAddrSpinner.setAdapter(addressAdapter);

                    for (int i = 0; i < response.body().size(); i++) {
                        if (ad_name.equals(response.body().get(i).getName())) {
                            address_id = response.body().get(i).getId();
                            cartBinding.cartAddrSpinner.setSelection(i);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {

            }
        });
    }

    private void getCartLists() {
        cartList = new ArrayList<>();
        Call<List<Cart>> call = ApiController.getInstance().getApi().getCart(1);
        call.enqueue(new Callback<List<Cart>>() {
            @Override
            public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(CartActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                }
                if (response.body() != null) {
                    cartList.addAll(response.body());
                    cartAdapter.setCartList(cartList);
                    cartBinding.recViewCart.setVisibility(View.VISIBLE);
                    cartBinding.tvEmpty.setVisibility(View.GONE);
                    try {
                        setPrices(cartList);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    cartBinding.recViewCart.setVisibility(View.GONE);
                    cartBinding.tvEmpty.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<Cart>> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onQuantityClick(Cart cart, View view) {
        PopupMenu popup = new PopupMenu(CartActivity.this, view);
        int qt = cart.getTotal_Quantity();
        for (int i = 1; i < qt + 1; i++) {
            popup.getMenu().add(Menu.NONE, i, i, "" + i);
        }

        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                updateQuantity(cart.getC_Id(), menuItem.getItemId());
                return false;
            }
        });
        popup.show();
    }

    private void updateQuantity(int c_id, int qnty) {
        Call<ResponseModel> call = ApiController.getInstance().getApi().updateQuantity(c_id, qnty);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        getCartLists();
                    } else {
                        Toast.makeText(CartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteClick(Cart cart, int position) {
        deleteCart(cart.getC_Id(), position);
    }

    private void deleteCart(int c_id, int pos) {
        Call<ResponseModel> call = ApiController.getInstance().getApi().deleteCart(c_id);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        cartList.remove(pos);
                        cartAdapter.setCartList(cartList);
                        if (cartList.size() == 0) {
                            cartBinding.recViewCart.setVisibility(View.GONE);
                            cartBinding.tvEmpty.setVisibility(View.VISIBLE);
                        }
                        try {
                            setPrices(cartList);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Toast.makeText(CartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(CartActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPrices(List<Cart> cartList) throws JSONException {
        double total_bag = 0;
        double total_discount = 0;
        double total_sale = 0;
        double shipping_charges = 0;
        double total_payable = 0;
        for (int i = 0; i < cartList.size(); i++) {
            total_bag = total_bag + cartList.get(i).getPrice() * cartList.get(i).getQuantity();
            total_discount = total_discount + (cartList.get(i).getPrice() - cartList.get(i).getSale_Price()) * cartList.get(i).getQuantity();
            total_sale = total_sale + cartList.get(i).getSale_Price() * cartList.get(i).getQuantity();

            if (cartList.get(i).getAddresses().contains(address_name)) {
                JSONArray jsonArray = new JSONArray(cartList.get(i).getAddresses());
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject obj = jsonArray.getJSONObject(j);
                    int id = obj.getInt("Id");
                    String name = obj.getString("Name");
                    double charge = obj.getDouble("Charge");
                    if (name.equals(address_name)) {
                        shipping_charges = shipping_charges + charge;
                    }
                }
            }
        }
        total_payable = total_sale + shipping_charges;
        total_price = total_payable;
        if (cartList.size() != 0) {
            seller_id = cartList.get(0).getS_id();
            seller_contacts = cartList.get(0).getSeller_Contacts();
            seller_accounts = cartList.get(0).getSeller_Accounts();
        }
        cartBinding.cartTotalBag.setText(getApplicationContext().getString(R.string.Rs) + total_bag);
        cartBinding.cartTotalDiscount.setText("-" + getApplicationContext().getString(R.string.Rs) + total_discount);
        cartBinding.cartTotalShippingCharges.setText(getApplicationContext().getString(R.string.Rs) + shipping_charges);
        cartBinding.cartTotalPayable.setText(getApplicationContext().getString(R.string.Rs) + total_payable);
        cartBinding.cartPayTotal.setText(getApplicationContext().getString(R.string.Rs) + total_payable);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Address clickedItem = (Address) adapterView.getItemAtPosition(i);
        address_id = clickedItem.getId();
        address_name = clickedItem.getName();
        cartAdapter.setAddress_name(address_name);
        try {
            setPrices(cartList);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        if (total_price != 0 &&
                address_id != 0 &&
                seller_id != 0 &&
                !seller_contacts.equals("") &&
                !seller_accounts.equals("")) {

            startActivity(new Intent(CartActivity.this, CheckoutActivity.class)
                    .putExtra("total_price", total_price)
                    .putExtra("address_id", address_id)
                    .putExtra("address_name", address_name)
                    .putExtra("seller_id", seller_id)
                    .putExtra("seller_contacts", seller_contacts)
                    .putExtra("seller_accounts", seller_accounts));

        }
    }
}