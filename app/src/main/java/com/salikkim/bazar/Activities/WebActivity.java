package com.salikkim.bazar.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import com.salikkim.bazar.R;
import com.salikkim.bazar.databinding.ActivityWebBinding;

public class WebActivity extends AppCompatActivity {
    private ActivityWebBinding webBinding;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webBinding = ActivityWebBinding.inflate(getLayoutInflater());
        View view = webBinding.getRoot();
        setContentView(view);
        webBinding.toolbarWebViewActivity.setTitle("");
        setSupportActionBar(webBinding.toolbarWebViewActivity);
        webBinding.toolbarWebViewActivity.setNavigationIcon(R.drawable.baseline_arrow_back);
        webBinding.toolbarWebViewActivity.setNavigationOnClickListener(v -> finish());
        String web = getIntent().getExtras().getString("web_view");
        if(web.equals("privacy")){
            url = "file:///android_asset/privacy_policy.html";
            webBinding.toolbarWebViewActivity.setTitle("Privacy Policy");
        }else if(web.equals("about")){
            url = "file:///android_asset/about.html";
            webBinding.toolbarWebViewActivity.setTitle("About");
        }
        webBinding.webView.setWebViewClient(new WebViewClient());
        webBinding.webView.loadUrl(url);

        WebSettings webSettings = webBinding.webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webBinding.webView.getSettings().setBuiltInZoomControls(false);
    }
}