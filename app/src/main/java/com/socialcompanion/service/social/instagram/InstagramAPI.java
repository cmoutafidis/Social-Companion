package com.socialcompanion.service.social.instagram;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.socialcompanion.service.social.instagramtasks.DownloadImageTask;
import com.socialcompanion.service.social.instagramtasks.DownloadWithoutSetImageTask;

import java.util.concurrent.ExecutionException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;

public class InstagramAPI {

    private static final String LOGIN_URL = "https://www.instagram.com/oauth/authorize/?client_id=43b993c419f94064b25ed722408807d9&redirect_uri=https://www.google.com&response_type=token";
    private static final String REDIRECT_URL = "https://www.google.com";
    private static Instagram4Android instagram = null;
    private static Bitmap profilePic;

    public static InstagramUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(InstagramUser currentUser) {
        InstagramAPI.currentUser = currentUser;
    }

    private static InstagramUser currentUser;

    public static Bitmap getProfilePic() {
        return profilePic;
    }

    public static void setProfilePic(Bitmap profilePic) {
        InstagramAPI.profilePic = profilePic;
    }

    public static String getLoginUrl() {
        return LOGIN_URL;
    }

    public static String getRedirectUrl() {
        return REDIRECT_URL;
    }

    public static void setInstagram(Instagram4Android instagram) {
        InstagramAPI.instagram = instagram;
    }

    public static Instagram4Android getInstagram() {

        return instagram;
    }

    public static boolean setBitmapByUrl(ImageView imageView, String url){
        DownloadImageTask downloadImageTask = new DownloadImageTask(imageView);
        try {
            downloadImageTask.execute(url).get();
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Bitmap getBitmapByUrl(String url){
        DownloadWithoutSetImageTask downloadImageTask = new DownloadWithoutSetImageTask();
        try {
            return downloadImageTask.execute(url).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
