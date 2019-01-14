package com.socialcompanion.service.social.instagram;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.socialcompanion.service.social.instagramtasks.DownloadImageTask;
import com.socialcompanion.service.social.instagramtasks.DownloadWithoutSetImageTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary;

public class InstagramAPI {

    private static final String LOGIN_URL = "https://www.instagram.com/oauth/authorize/?client_id=43b993c419f94064b25ed722408807d9&redirect_uri=https://www.google.com&response_type=token";
    private static final String REDIRECT_URL = "https://www.google.com";
    private static String username;
    private static String password;
    private static Instagram4Android instagram = null;
    private static Bitmap profilePic;
    private static InstagramUser currentUser;
    private static List<InstagramUserObject> following;
    private static List<InstagramUserObject> followers;
    private static List<InstagramUserObject> nonFollowers = new ArrayList<>();
    private static List<InstagramUserObject> nonFollowing = new ArrayList<>();
    private static List<InstagramUserObject> mutualFollowing = new ArrayList<>();

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        InstagramAPI.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        InstagramAPI.password = password;
    }

    public static List<InstagramUserObject> getMutualFollowing() {
        return mutualFollowing;
    }

    public static void setMutualFollowing(List<InstagramUserObject> mutualFollowing) {
        InstagramAPI.mutualFollowing = mutualFollowing;
    }

    public static List<InstagramUserObject> getNonFollowers() {
        return nonFollowers;
    }

    public static void setNonFollowers(List<InstagramUserObject> nonFollowers) {
        InstagramAPI.nonFollowers = nonFollowers;
    }

    public static List<InstagramUserObject> getNonFollowing() {
        return nonFollowing;
    }

    public static void setNonFollowing(List<InstagramUserObject> nonFollowing) {
        InstagramAPI.nonFollowing = nonFollowing;
    }

    public static void addToNonFollowers(InstagramUserObject object){
        nonFollowers.add(object);
    }

    public static void addToMutualFollowing(InstagramUserObject object){
        mutualFollowing.add(object);
    }

    public static void addToNonFollowing(InstagramUserObject object){
        nonFollowing.add(object);
    }

    public static List<InstagramUserObject> getFollowers() {
        return followers;
    }

    public static void setFollowers(List<InstagramUserObject> followers) {
        InstagramAPI.followers = followers;
    }

    public static List<InstagramUserObject> getFollowing() {
        return following;
    }

    public static void setFollowing(List<InstagramUserObject> following) {
        InstagramAPI.following = following;
    }

    public static InstagramUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(InstagramUser currentUser) {
        InstagramAPI.currentUser = currentUser;
    }

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
