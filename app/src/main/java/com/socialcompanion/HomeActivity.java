package com.socialcompanion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    public static HomeActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        instance = this;

        sharedPref = getSharedPreferences("socialAccess", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        String accessToken = sharedPref.getString("access_token", "");

        if(!accessToken.equals("")){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("access_token", accessToken);
            this.startActivity(intent);
        }

        Button instagramButton = findViewById(R.id.instagramButton);
        instagramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WebPageActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }
}
