package com.socialcompanion.service.social.instagramtasks;

import android.os.AsyncTask;

import com.socialcompanion.service.social.instagram.InstagramAPI;

import java.io.IOException;

import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsernameResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;

public class GetUserTask extends AsyncTask<String, Void, InstagramUser> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected InstagramUser doInBackground(String... strings) {
        InstagramSearchUsernameResult result = null;
        try {
            result = InstagramAPI.getInstagram().sendRequest(new InstagramSearchUsernameRequest("charismoutafidis"));
            return result.getUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(InstagramUser result) {

    }
}
