package com.ensias.healthcareapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class vaccawarness extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccinelayout);

        webView = (WebView) findViewById(R.id.webview2);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://sites.google.com/view/vaccinationawarness/home");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }


}