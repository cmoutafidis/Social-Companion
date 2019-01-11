package com.socialcompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.socialcompanion.service.social.instagram.InstagramAPI;

public class WebPageActivity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        webview =(WebView)findViewById(R.id.webView);

        webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl(InstagramAPI.getLoginUrl());

        webview.setWebViewClient(new WebViewClient() {


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println();
                if(url.startsWith(InstagramAPI.getRedirectUrl())){
                    String accessToken = url.split("access_token=")[1];
                    Intent myIntent = new Intent(WebPageActivity.this, MainActivity.class);
                    myIntent.putExtra("access_token", accessToken);
                    WebPageActivity.this.startActivity(myIntent);
                    WebPageActivity.this.finish();
                }
                return false; //Indicates WebView to NOT load the url;
            }
        });
    }
}
