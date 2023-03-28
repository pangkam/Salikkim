package com.salikkim.bazar.Activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private String address_name;
    private String user_id;
    private String MY_PREFS_NAME = "User";
    private String mobile;
    private String alt_mobile;
    private ActivityResultLauncher<Intent> imageActivityResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Uri image = result.getData().getData();
                            String selectedImage = FileUtils.getPath(CheckoutActivity.this, image);
                            uploadScreenshot(selectedImage);
                        }
                    }
                });

        checkoutBinding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        View view = checkoutBinding.getRoot();
        setContentView(view);
        double total_price = getIntent().getExtras().getDouble("total_price");
        String seller_ac = getIntent().getExtras().getString("seller_acc");


        SharedPreferences prefs = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        user_id = prefs.getString("user_id", null);
        address_name = prefs.getString("address_name", null);
        mobile = prefs.getString("mobile", null);
        alt_mobile = prefs.getString("alt_mobile", null);

        checkoutBinding.tvPriceProceed.setText(getApplicationContext().getString(R.string.Rs) + String.valueOf(total_price));
        checkoutBinding.tvAddressProceed.setText(address_name);
        checkoutBinding.tvContact.setText(mobile);
        checkoutBinding.tvOptContact.setText(alt_mobile);
        checkoutBinding.btnCloseProceed.setOnClickListener(this);
        checkoutBinding.btnUploadCheckout.setOnClickListener(this);
        checkoutBinding.btnAddContactProceed.setOnClickListener(this);

        paymentAdapter = new PaymentAdapter(CheckoutActivity.this, paymentList);
        checkoutBinding.viewPagerPayment.setAdapter(paymentAdapter);

        try {
            getPaymentLists(seller_ac);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void getPaymentLists(String seller_ac) throws JSONException {
        JSONArray jsonArray = new JSONArray(seller_ac);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String mode = obj.getString("Mode");
            String id = obj.getString("Id");
            String number = obj.getString("Number");
            String screenshot = obj.getString("Img");
            paymentList.add(new Payment(screenshot, id, number, mode));
            paymentAdapter.notifyItemChanged(i, paymentList);
        }

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
            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imageActivityResultLauncher.launch(i);
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


    private void uploadScreenshot(String path) {
        File file = new File(Uri.parse(path).getPath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody u_id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), address_name);

        Call<ResponseModel> call = ApiController.getInstance().getApi().addOrder(image, u_id, address);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        new AlertDialog.Builder(CheckoutActivity.this)
                                .setTitle(response.body().getMessage())
                                .setMessage(R.string.order_success_notice)
                                .setCancelable(false)
                                .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
                                finish();
                            }
                        }).show();
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
}