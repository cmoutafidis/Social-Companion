package com.socialcompanion.service.social.instagramtasks;

import android.os.AsyncTask;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.payload.InstagramLoginResult;

public class LoginTask extends AsyncTask<String, Void, Instagram4Android> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Instagram4Android doInBackground(String... credentials) {
        Instagram4Android instagram = Instagram4Android.builder().username(credentials[0]).password(credentials[1]).build();
        InstagramLoginResult result = null;
        instagram.setup();
        try {
            while(result == null || !result.getStatus().equals("ok")){
                result = instagram.login();
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instagram;
    }

    @Override
    protected void onPostExecute(Instagram4Android result) {

    }
}
