package com.salikkim.bazar.Fragments;

import android.app.Dialog;
import android.content.Context;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.salikkim.bazar.Adapters.ArrayAddressAdapter;
import com.salikkim.bazar.Helper.ApiController;
import com.salikkim.bazar.Models.Address;
import com.salikkim.bazar.Models.ResponseModel;
import com.salikkim.bazar.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetupAcDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private Spinner spinner;
    private int address_id;
    private String add_name;
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

    public SetupAcDialog(@NonNull Context context, String add_name) {
        super(context);
        this.add_name = add_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.account_setup_dialog);
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

        spinner = findViewById(R.id.ac_addr_spinner);
        btn_save.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        spinner.setOnItemSelectedListener(this);
        getAddressLists(add_name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close_ac_dialog:
                dismiss();
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
        } else if (add_name == null) {
            Toast.makeText(getContext(), "Please select address", Toast.LENGTH_SHORT).show();
        } else {
            setProfile();
        }
    }

    private void setProfile() {
        btn_save.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        Call<ResponseModel> call = ApiController.getInstance().getApi().setProfile(
                "7005643266",
                ti_name.getText().toString().trim(),
                ti_mobile.getText().toString().trim(),
                ti_alt_mobile.getText().toString().trim(),
                ti_email.getText().toString().trim(),
                address_id);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.body() != null) {
                    if (response.body().getStatus()) {
                        Toast.makeText(getContext(), "Profile saved", Toast.LENGTH_SHORT).show();
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

    private void getAddressLists(String ad_name) {
        Call<List<Address>> call = ApiController.getInstance().getApi().getAddress();
        call.enqueue(new Callback<List<Address>>() {
            @Override
            public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body() != null) {
                    ArrayAddressAdapter addressAdapter = new ArrayAddressAdapter(getContext(), response.body());
                    spinner.setAdapter(addressAdapter);

                    for (int i = 0; i < response.body().size(); i++) {
                        if (ad_name.equals(response.body().get(i).getName())) {
                            address_id = response.body().get(i).getId();
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
        add_name = clickedItem.getName();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
