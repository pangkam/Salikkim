package com.salikkim.bazar.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.salikkim.bazar.Activities.FavoriteActivity;
import com.salikkim.bazar.Activities.OrderActivity;
import com.salikkim.bazar.Activities.WebActivity;
import com.salikkim.bazar.Adapters.CategoryAdapter;
import com.salikkim.bazar.Helper.SetupAcDialog;
import com.salikkim.bazar.R;
import com.salikkim.bazar.databinding.FragmentAccountBinding;
import com.salikkim.bazar.databinding.FragmentCategoryBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {
    private FragmentAccountBinding accountBinding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_order_account:
                startActivity(new Intent(getActivity(), OrderActivity.class));
                break;
            case R.id.btn_privacy:
                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("web_view", "privacy"));
                break;
            case R.id.btn_about:
                startActivity(new Intent(getActivity(), WebActivity.class).putExtra("web_view", "about"));
                break;
            case R.id.btn_account_settings:
                SetupAcDialog setupAcDialog = new SetupAcDialog(getActivity());
                setupAcDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                setupAcDialog.setCancelable(true);
                setupAcDialog.show();
                Window window = setupAcDialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                break;

            case R.id.btn_account_fav:
                startActivity(new Intent(getActivity(), FavoriteActivity.class));
                break;

        }
    }
}