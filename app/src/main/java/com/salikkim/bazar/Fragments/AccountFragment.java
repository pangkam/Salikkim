package com.salikkim.bazar.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.salikkim.bazar.Activities.CartActivity;
import com.salikkim.bazar.Activities.FavoriteActivity;
import com.salikkim.bazar.Activities.OrderActivity;
import com.salikkim.bazar.Activities.WebActivity;
import com.salikkim.bazar.BuildConfig;
import com.salikkim.bazar.R;
import com.salikkim.bazar.databinding.FragmentAccountBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {
    private FragmentAccountBinding accountBinding;
    private String user_id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String MY_PREFS_NAME = "User";
    private int address_id;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accountBinding = FragmentAccountBinding.inflate(inflater, container, false);
        View view = accountBinding.getRoot();
        accountBinding.btnOrderAccount.setOnClickListener(this);
        accountBinding.btnPrivacy.setOnClickListener(this);
        accountBinding.btnAbout.setOnClickListener(this);
        accountBinding.btnAccountSettings.setOnClickListener(this);
        accountBinding.btnAccountFav.setOnClickListener(this);
        accountBinding.btnAccountCart.setOnClickListener(this);
        accountBinding.btnShareApp.setOnClickListener(this);
        accountBinding.btnBecomeSeller.setOnClickListener(this);
        accountBinding.btnRateApp.setOnClickListener(this);
        SharedPreferences prefs = getActivity().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        user_id = prefs.getString("user_id", null);
        address_id = prefs.getInt("address_id", 0);
        String user_name = prefs.getString("user_name", null);
        String mobile = prefs.getString("mobile", null);
        String alt_mobile = prefs.getString("alt_mobile", null);
        String email = prefs.getString("email", null);
        String address_name = prefs.getString("address_name", null);
        setViews(user_name, mobile, alt_mobile, email, address_name);
        if (user_id != null) {
            accountBinding.acLayoutAccount.setVisibility(View.VISIBLE);
        } else {
            accountBinding.acLayoutAccount.setVisibility(View.GONE);
        }
        return view;
    }

    private void setViews(String user_name, String mobile, String alt_mobile, String email, String address_name) {
        accountBinding.accountName.setText(user_name);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(user_name);
        stringBuilder.append("\nMobile: " + mobile);
        stringBuilder.append("\nAlt Mobile: " + alt_mobile);
        stringBuilder.append("\nEmail: " + email);
        stringBuilder.append("\nAddress: " + address_name);
        accountBinding.accountDetails.setText(stringBuilder);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_order_account:
                if (user_id != null) {
                    startActivity(new Intent(getActivity(), OrderActivity.class).putExtra("user_id", user_id));
                }
                break;
            case R.id.btn_account_fav:
                if (user_id != null) {
                    startActivity(new Intent(getActivity(), FavoriteActivity.class).putExtra("user_id", user_id));
                }
                break;
            case R.id.btn_account_cart:
                if (user_id != null) {
                    startActivity(new Intent(getActivity(), CartActivity.class));
                }
                break;
            case R.id.btn_account_logout:
                Toast.makeText(getActivity(), "Log out", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_become_seller:
                Toast.makeText(getActivity(), "Please contact us", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_share_app:
                shareApp();
                break;
            case R.id.btn_rate_app:
                rateApp();
                break;
            case R.id.btn_privacy:
                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("web_view", "privacy"));
                break;
            case R.id.btn_about:
                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("web_view", "about"));
                break;
            case R.id.btn_account_settings:
                SetupAcDialog setupAcDialog = new SetupAcDialog(getActivity(), address_id,false);
                setupAcDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                setupAcDialog.setCancelable(true);
                setupAcDialog.show();
                Window window = setupAcDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                break;
        }
    }

    private void rateApp() {
        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getActivity().getPackageName());
        Intent rate = new Intent(Intent.ACTION_VIEW, uri);
        rate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(rate);
    }

    private void shareApp() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Salikkim");
            String shareMessage = "\nPurchase fashionable clothing and shoes\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share with"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}