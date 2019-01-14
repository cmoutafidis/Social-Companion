package com.socialcompanion.service.social.instagramtasks;

import android.os.AsyncTask;

import com.socialcompanion.service.social.instagram.InstagramAPI;
import com.socialcompanion.service.social.instagram.InstagramUserObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowersRequest;
import dev.niekirk.com.instagram4android.requests.InstagramGetUserFollowingRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramGetUserFollowersResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary;

public class GetFollowersRequest extends AsyncTask<Long, Void, List<InstagramUserObject>> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<InstagramUserObject> doInBackground(Long... ids) {
        InstagramGetUserFollowersResult result = null;
        try {
            List<InstagramUserObject> users = new ArrayList<>();
            result = InstagramAPI.getInstagram().sendRequest(new InstagramGetUserFollowersRequest(ids[0]));
            for(InstagramUserSummary curUser : result.getUsers()){
                InstagramUserObject object = new InstagramUserObject();
                object.setProfilePicUrl(curUser.getProfile_pic_url());
                object.setUserFullName(curUser.getFull_name());
                object.setUsername(curUser.getUsername());
                if(curUser.getProfile_pic_id() != null){
                    object.setUserId(Long.parseLong(curUser.getProfile_pic_id().split("_")[1]));
                }
                else{
                    System.out.println();
                }
                users.add(object);
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<InstagramUserObject> result) {

    }
}
