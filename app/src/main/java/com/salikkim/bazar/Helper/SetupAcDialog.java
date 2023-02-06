package com.salikkim.bazar.Helper;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.salikkim.bazar.R;

public class SetupAcDialog extends Dialog implements View.OnClickListener {

    public SetupAcDialog(@NonNull Activity context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.account_setup_dialog);
        ImageView btn_close = findViewById(R.id.btn_close_ac_dialog);
        btn_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_close_ac_dialog:
                dismiss();
                break;
        }
    }
}
