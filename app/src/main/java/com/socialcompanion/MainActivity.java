package com.socialcompanion;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.socialcompanion.menufragments.AllFollowingFragment;
import com.socialcompanion.menufragments.BlacklistFragment;
import com.socialcompanion.menufragments.FansFragment;
import com.socialcompanion.menufragments.MutualFragment;
import com.socialcompanion.menufragments.NonFollowersFragment;
import com.socialcompanion.menufragments.WhitelistFragment;

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

        String accessToken = getIntent().getExtras().getString("access_token");
        editor.putString("access_token", accessToken);
        editor.apply();

        HomeActivity.instance.finish();

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
                    new NonFollowersFragment()).commit();
            navigationView.setCheckedItem(R.id.non_followers);
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
