package com.salikkim.bazar.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.salikkim.bazar.databinding.ActivityOtpactivityBinding;

public class OTPActivity extends AppCompatActivity {
    private ActivityOtpactivityBinding otpactivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        otpactivityBinding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        View view = otpactivityBinding.getRoot();
        setContentView(view);
    }
}