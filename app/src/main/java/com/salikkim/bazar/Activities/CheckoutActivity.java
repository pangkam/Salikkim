package com.salikkim.bazar.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.salikkim.bazar.Adapters.PaymentAdapter;
import com.salikkim.bazar.Helper.ApiController;
import com.salikkim.bazar.Helper.FileUtils;
import com.salikkim.bazar.Models.Payment;
import com.salikkim.bazar.Models.ResponseModel;
import com.salikkim.bazar.R;
import com.salikkim.bazar.databinding.ActivityCheckoutBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityCheckoutBinding checkoutBinding;
    private PaymentAdapter paymentAdapter;
    private List<Payment> paymentList = new ArrayList<>();
    private String address_name = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkoutBinding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        View view = checkoutBinding.getRoot();
        setContentView(view);
        double total_price = getIntent().getExtras().getDouble("total_price");
        int address_id = getIntent().getExtras().getInt("address_id");
        address_name = getIntent().getExtras().getString("address_name");
        int seller_id = getIntent().getExtras().getInt("seller_id");
        String seller_contacts = getIntent().getExtras().getString("seller_contacts");
        String seller_accounts = getIntent().getExtras().getString("seller_accounts");
        checkoutBinding.tvPriceProceed.setText(getApplicationContext().getString(R.string.Rs) + String.valueOf(total_price));
        checkoutBinding.tvAddressProceed.setText(address_name);
        checkoutBinding.btnCloseProceed.setOnClickListener(this);
        checkoutBinding.btnUploadCheckout.setOnClickListener(this);
        checkoutBinding.btnAddContactProceed.setOnClickListener(this);

        paymentAdapter = new PaymentAdapter(CheckoutActivity.this, paymentList);
        checkoutBinding.viewPagerPayment.setAdapter(paymentAdapter);

        try {
            getPaymentLists(seller_accounts);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPaymentLists(String seller_acs) throws JSONException {
        JSONArray jsonArray = new JSONArray(seller_acs);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String mode = obj.getString("Mode");
            String id = obj.getString("Id");
            String number = obj.getString("Number");
            String screenshot = obj.getString("Img");
            paymentList.add(new Payment(screenshot, id, number, mode));
        }
        paymentAdapter.notifyDataSetChanged();


        TabLayoutMediator layoutMediator = new TabLayoutMediator(checkoutBinding.paymentTaLayout, checkoutBinding.viewPagerPayment, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (paymentList.get(position).getMode()) {
                    case "GooglePay":
                        tab.setIcon(R.drawable.gpay);
                        break;
                    case "Paytm":
                        tab.setIcon(R.drawable.paytm);
                        break;
                    case "PhonePe":
                        tab.setIcon(R.drawable.phonepe);
                        break;
                }
            }
        });

        layoutMediator.attach();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close_proceed:
                new AlertDialog.Builder(this).setMessage("Are you sure you want to cancel this order?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).setNegativeButton(android.R.string.no, null).show();
                break;
            case R.id.btn_upload_checkout:
                pickImage();
                break;

        }
    }

    private void pickImage() {
        if (ContextCompat.checkSelfPermission(CheckoutActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CheckoutActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select image"), 1);
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setMessage("Are you sure you want to cancel this order?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("No", null).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1 && null != data) {
                Uri img_url = data.getData();
                Uri imagePath = Uri.parse(FileUtils.getPath(CheckoutActivity.this, img_url));
                File file = new File(imagePath.getPath());
                uploadScreenshot(file);
            }
        }
    }

    private void uploadScreenshot(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody customer_name = RequestBody.create(MediaType.parse("text/plain"), "Pangkam Damang");
        RequestBody user_id = RequestBody.create(MediaType.parse("text/plain"), "1");
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), address_name);

        Call<ResponseModel> call = ApiController.getInstance().getApi().uploadScreenshot(filePart, customer_name, user_id, address);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        showAlertDialog(response.body().getMessage());
                    } else {
                        Toast.makeText(CheckoutActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(CheckoutActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAlertDialog(String s) {
        new AlertDialog.Builder(CheckoutActivity.this).setTitle(s).setMessage(R.string.order_success_notice).setCancelable(false).setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
                finish();
            }
        }).show();
    }
}