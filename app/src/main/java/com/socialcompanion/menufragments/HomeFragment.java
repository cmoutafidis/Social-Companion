package com.socialcompanion.menufragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.socialcompanion.R;
import com.socialcompanion.service.social.instagram.InstagramAPI;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        ImageView imageView = view.findViewById(R.id.accountProfileImage);
        TextView accountName = view.findViewById(R.id.accountName);
        TextView username = view.findViewById(R.id.username);
        TextView following = view.findViewById(R.id.accountFollowing);
        TextView followers = view.findViewById(R.id.accountFollowers);
        TextView mediaCount = view.findViewById(R.id.mediaCount);

        imageView.setImageBitmap(InstagramAPI.getProfilePic());
        accountName.setText(InstagramAPI.getCurrentUser().getFull_name());
        followers.setText(Integer.toString(InstagramAPI.getCurrentUser().getFollower_count()));
        following.setText(Integer.toString(InstagramAPI.getCurrentUser().getFollowing_count()));
        username.setText(InstagramAPI.getCurrentUser().getUsername());
        mediaCount.setText("A total of " + InstagramAPI.getCurrentUser().getMedia_count() + " posts.");

        return view;
    }
}
