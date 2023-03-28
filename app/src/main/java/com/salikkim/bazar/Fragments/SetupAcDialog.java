package com.salikkim.bazar.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.salikkim.bazar.Activities.MainActivity;
import com.salikkim.bazar.Adapters.ArrayAddressAdapter;
import com.salikkim.bazar.Helper.ApiController;
import com.salikkim.bazar.Models.Address;
import com.salikkim.bazar.Models.ResponseModel;
import com.salikkim.bazar.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetupAcDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Activity activity;
    private Spinner spinner;
    private TextInputEditText ti_name;
    private TextInputEditText ti_mobile;
    private TextInputEditText ti_alt_mobile;
    private TextInputEditText ti_email;
    private TextInputLayout tl_name;
    private TextInputLayout tl_mobile;

    private TextInputLayout tl_alt_mobile;
    private TextInputLayout tl_email;
    private TextView btn_save;
    private ProgressBar progressBar;
    private String MY_PREFS_NAME = "User";
    private String address_name;
    private String user_id;
    private String user_name;
    private String mobile;
    private String alt_mobile;
    private String email;
    private int address_id;
    private final boolean isUpdate;

    public SetupAcDialog(@NonNull Activity activity, int address_id, boolean isUpdate) {
        super(activity);
        this.activity = activity;
        this.address_id = address_id;
        this.isUpdate = isUpdate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.account_setup_dialog);

        SharedPreferences prefs = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        user_id = prefs.getString("user_id", null);
        user_name = prefs.getString("user_name", null);
        mobile = prefs.getString("mobile", null);
        alt_mobile = prefs.getString("alt_mobile", null);
        address_id = prefs.getInt("address_id", 0);
        address_name = prefs.getString("address_name", null);
        email = prefs.getString("email", null);

        ImageView btn_close = findViewById(R.id.btn_close_ac_dialog);
        btn_save = findViewById(R.id.btn_save_ac_dialog);
        progressBar = findViewById(R.id.progress_ac_dialog);

        tl_name = findViewById(R.id.tl_name_dialog);
        ti_name = findViewById(R.id.ti_Name_dialog);

        tl_mobile = findViewById(R.id.tl_mobile_dialog);
        ti_mobile = findViewById(R.id.ti_mobile_dialog);

        // tl_alt_mobile = findViewById(R.id.tl_alt_mobile_dialog);
        ti_alt_mobile = findViewById(R.id.ti_alt_number_dialog);

        // tl_email = findViewById(R.id.tl_email_dialog);
        ti_email = findViewById(R.id.ti_email_dialog);

        ti_name.setText(user_name);
        ti_mobile.setText(mobile);
        ti_alt_mobile.setText(alt_mobile);
        ti_email.setText(email);

        spinner = findViewById(R.id.ac_addr_spinner);
        btn_save.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
        getAddressLists();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close_ac_dialog:
                if (isUpdate) {
                    System.exit(0);
                } else {
                    dismiss();
                }
                break;
            case R.id.btn_save_ac_dialog:
                getTexts();
                break;
        }
    }

    private void getTexts() {
        if (ti_name.getText().toString().isEmpty()) {
            tl_name.setError("Name cannot be blank");
        } else if (ti_mobile.getText().toString().isEmpty()) {
            tl_mobile.setError("Enter mobile number registered with WhatsApp");
        } else {
            setProfile(user_id,
                    ti_name.getText().toString().trim(),
                    ti_mobile.getText().toString().trim(),
                    ti_alt_mobile.getText().toString().trim(),
                    ti_email.getText().toString().trim(),
                    address_id);
        }
    }

    private void setProfile(String user_id, String name, String mobile, String alt_mobile, String email, int address_id) {
        btn_save.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseModel> call = ApiController.getInstance().getApi().setProfile(
                user_id, name, mobile, alt_mobile, email, address_id);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        SharedPreferences.Editor editor = getContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString("user_id", user_id);
                        editor.putInt("address_id", address_id);
                        editor.putString("address_name", address_name);
                        editor.putString("user_name", name);
                        editor.putString("mobile", mobile);
                        editor.putString("alt_mobile", alt_mobile);
                        editor.putString("email", email);
                        editor.apply();
                        new AlertDialog.Builder(getContext()).setMessage("Profile Saved Successfully").setCancelable(false)
                                .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(getContext(),
                                                MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        getContext().startActivity(intent);
                                        activity.finish();
                                    }
                                }).show();
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    btn_save.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getAddressLists() {
        Call<List<Address>> call = ApiController.getInstance().getApi().getAddress();
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body() != null) {
                    List<Address> addresses = new ArrayList<>();
                    addresses.add(new Address(0, "Select Address", null));
                    addresses.addAll(response.body());
                    ArrayAddressAdapter addressAdapter = new ArrayAddressAdapter(getContext(), addresses);
                    spinner.setAdapter(addressAdapter);

                    for (int i = 0; i < addresses.size(); i++) {
                        if (address_id == addresses.get(i).getId()) {
                            spinner.setSelection(i);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Address>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Address clickedItem = (Address) adapterView.getItemAtPosition(i);
        address_id = clickedItem.getId();
        address_name = clickedItem.getName();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
