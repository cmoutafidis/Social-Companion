package com.socialcompanion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.socialcompanion.service.social.instagram.InstagramAPI;
import com.socialcompanion.service.social.instagramtasks.LoginTask;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import dev.niekirk.com.instagram4android.Instagram4Android;

public class WebPageActivity extends AppCompatActivity {

    private WebView webview;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page);

        Button loginButton = findViewById(R.id.loginButton);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                LoginTask loginTask = new LoginTask();
                try {
                    InstagramAPI.setInstagram(loginTask.execute(username, password).get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                if(InstagramAPI.getInstagram().getLastResponse().code() == 200){
                    Intent myIntent = new Intent(WebPageActivity.this, MainActivity.class);
                    myIntent.putExtra("username", username);
                    myIntent.putExtra("password", password);
                    WebPageActivity.this.startActivity(myIntent);
                    WebPageActivity.this.finish();
                }
            }
        });
    }
}
