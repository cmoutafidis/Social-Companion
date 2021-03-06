package com.socialcompanion.service.social.instagramtasks;

import android.os.AsyncTask;

import com.socialcompanion.service.social.instagram.InstagramAPI;
import com.socialcompanion.service.social.instagram.InstagramUserObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowingRequest;
import dev.niekirk.com.instagram4android.requests.InstagramUnfollowRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramGetUserFollowersResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary;
import dev.niekirk.com.instagram4android.requests.payload.StatusResult;

public class UnfollowRequest extends AsyncTask<Long, Void, StatusResult> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected StatusResult doInBackground(Long... ids) {
        StatusResult result = null;
        try {
            List<InstagramUserObject> users = new ArrayList<>();
            result = InstagramAPI.getInstagram().sendRequest(new InstagramUnfollowRequest(ids[0]));
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(StatusResult result) {

    }
}
