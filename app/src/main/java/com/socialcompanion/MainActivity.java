package com.socialcompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socialcompanion.menufragments.AllFollowingFragment;
import com.socialcompanion.menufragments.BlacklistFragment;
import com.socialcompanion.menufragments.FansFragment;
import com.socialcompanion.menufragments.HomeFragment;
import com.socialcompanion.menufragments.MutualFragment;
import com.socialcompanion.menufragments.NonFollowersFragment;
import com.socialcompanion.menufragments.WhitelistFragment;
import com.socialcompanion.service.social.instagram.InstagramAPI;
import com.socialcompanion.service.social.instagram.InstagramUserObject;
import com.socialcompanion.service.social.instagramtasks.GetFollowersRequest;
import com.socialcompanion.service.social.instagramtasks.GetFollowingRequest;
import com.socialcompanion.service.social.instagramtasks.GetUserIdClass;
import com.socialcompanion.service.social.instagramtasks.GetUserTask;
import com.socialcompanion.service.social.instagramtasks.LoginTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutionException;

import dev.niekirk.com.instagram4android.Instagram4Android;
import dev.niekirk.com.instagram4android.requests.InstagramSearchUsernameRequest;
import dev.niekirk.com.instagram4android.requests.payload.InstagramSearchUsernameResult;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUser;
import dev.niekirk.com.instagram4android.requests.payload.InstagramUserSummary;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences("socialAccess", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        String username = getIntent().getExtras().getString("username");
        editor.putString("username", username);
        String password = getIntent().getExtras().getString("password");
        editor.putString("password", password);
        editor.apply();

        HomeActivity.instance.finish();

        LoginTask loginTask = new LoginTask();
        try {
            InstagramAPI.setInstagram(loginTask.execute(username, password).get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        InstagramAPI.setUsername(username);
        InstagramAPI.setPassword(password);

        updateUserInformation();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, this.drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.home);
        }

        NavigationView navView = findViewById(R.id.nav_view);
        View linearLayout = navView.getHeaderView(0);
        TextView textView = linearLayout.findViewById(R.id.accountName);
        TextView textView2 = linearLayout.findViewById(R.id.accountEmail);
        ImageView imageView = linearLayout.findViewById(R.id.accountImage);
        textView.setText(InstagramAPI.getCurrentUser().getFull_name());
        textView2.setText(InstagramAPI.getCurrentUser().getUsername());
        imageView.setImageBitmap(InstagramAPI.getProfilePic());
    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }

    public static void updateUserInformation() {
        GetUserTask getUserTask = new GetUserTask();
        try {
            InstagramAPI.setCurrentUser(getUserTask.execute(InstagramAPI.getUsername(), InstagramAPI.getPassword()).get());
            InstagramAPI.setProfilePic(InstagramAPI.getBitmapByUrl(InstagramAPI.getCurrentUser().getProfile_pic_url()));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        GetFollowingRequest getFollowingRequest = new GetFollowingRequest();
        try {
            InstagramAPI.setFollowing(getFollowingRequest.execute(Long.parseLong(InstagramAPI.getCurrentUser().getProfile_pic_id().split("_")[1])).get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        GetFollowersRequest getFollowersRequest = new GetFollowersRequest();
        try {
            InstagramAPI.setFollowers(getFollowersRequest.execute(Long.parseLong(InstagramAPI.getCurrentUser().getProfile_pic_id().split("_")[1])).get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<InstagramUserObject> followers = InstagramAPI.getFollowers();
        List<InstagramUserObject> following = InstagramAPI.getFollowing();
        InstagramAPI.setNonFollowing(new ArrayList<InstagramUserObject>());
        InstagramAPI.setNonFollowers(new ArrayList<InstagramUserObject>());
        InstagramAPI.setMutualFollowing(new ArrayList<InstagramUserObject>());

        for (InstagramUserObject curFollower : followers){
            boolean included = false;
            for (InstagramUserObject curFollowing : following){
                if(curFollower.getUsername().equals(curFollowing.getUsername())){
                    included = true;
                }
            }

            if(!included){
                InstagramAPI.addToNonFollowing(curFollower);
            }
        }

        for (InstagramUserObject curFollower : following){
            boolean included = false;
            for (InstagramUserObject curFollowing : followers){
                if(curFollower.getUsername().equals(curFollowing.getUsername())){
                    included = true;
                }
            }

            if(!included){
                InstagramAPI.addToNonFollowers(curFollower);
            }
        }

        for (InstagramUserObject curFollower : InstagramAPI.getFollowers()){
            for (InstagramUserObject curFollowing : InstagramAPI.getFollowing()){
                if(curFollower.getUsername().equals(curFollowing.getUsername())){
                    InstagramAPI.addToMutualFollowing(curFollower);
                }
            }
        }

        getUserImages();
    }

    private static void getUserImages() {
        List<InstagramUserObject> followers = InstagramAPI.getFollowers();
        List<InstagramUserObject> following = InstagramAPI.getFollowing();

        for (InstagramUserObject curFollower : followers){
            curFollower.setBitmapImage(InstagramAPI.getBitmapByUrl(curFollower.getProfilePicUrl()));
        }

        for (InstagramUserObject curFollower : following){
            curFollower.setBitmapImage(InstagramAPI.getBitmapByUrl(curFollower.getProfilePicUrl()));
        }
    }

    @Override
    public void onBackPressed() {
        if(this.drawerLayout.isDrawerOpen(GravityCompat.START)){
            this.drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.non_followers:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new NonFollowersFragment()).commit();
                break;
            case R.id.fans:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FansFragment()).commit();
                break;
            case R.id.mutual_following:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MutualFragment()).commit();
                break;
            case R.id.all_following:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AllFollowingFragment()).commit();
                break;
            case R.id.whitelist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WhitelistFragment()).commit();
                break;
            case R.id.blacklist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new BlacklistFragment()).commit();
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
