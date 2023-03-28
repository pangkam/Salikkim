package com.salikkim.bazar.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.salikkim.bazar.Fragments.SetupAcDialog;
import com.salikkim.bazar.Helper.ApiController;
import com.salikkim.bazar.Models.User;
import com.salikkim.bazar.databinding.ActivitySplashBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding splashBinding;
    private String MY_PREFS_NAME = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view = splashBinding.getRoot();
        setContentView(view);
        getUserProfile("7627950528");
    }

    private void getUserProfile(String s) {
        if (s != null) {
            Call<List<User>> call = ApiController.getInstance().getApi().getUserProfile(s);
            call.enqueue(new Callback<List<User>>() {
                @Override
                public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                    if (response.body() != null) {
                        setSharePrefs(response.body().get(0).getU_Id(),
                                response.body().get(0).getAddress_id(),
                                response.body().get(0).getAddress_Name(),
                                response.body().get(0).getName(),
                                response.body().get(0).getMobile(),
                                response.body().get(0).getAlt_Mobile(),
                                response.body().get(0).getEmail());
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    } else {
                        setSharePrefs(s, 0, null, null, null, null, null);
                        SetupAcDialog setupAcDialog = new SetupAcDialog(SplashActivity.this, 0, true);
                        setupAcDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        setupAcDialog.setCancelable(true);
                        setupAcDialog.show();
                        Window window = setupAcDialog.getWindow();
                        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    }
                }

                @Override
                public void onFailure(Call<List<User>> call, Throwable t) {
                    setSharePrefs(null, 1, null, null, null, null, null);
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            });
        } else {
            setSharePrefs(null, 1, null, null, null, null, null);
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    private void setSharePrefs(String u_id, int address_id, String address_name, String name, String mobile, String alt_mobile, String email) {
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("user_id", u_id);
        editor.putInt("address_id", address_id);
        editor.putString("address_name", address_name);
        editor.putString("user_name", name);
        editor.putString("mobile", mobile);
        editor.putString("alt_mobile", alt_mobile);
        editor.putString("email", email);
        editor.apply();
    }
}