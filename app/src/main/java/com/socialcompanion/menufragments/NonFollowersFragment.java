package com.socialcompanion.menufragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.socialcompanion.R;
import com.socialcompanion.service.social.instagram.CustomAdapter;
import com.socialcompanion.service.social.instagram.InstagramAPI;
import com.socialcompanion.service.social.instagram.InstagramUserObject;

import java.util.ArrayList;

public class NonFollowersFragment extends Fragment {

    ArrayList<InstagramUserObject> dataModels;
    ListView listView;
    private static CustomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_non_followers, container, false);
        listView = view.findViewById(R.id.list);
        dataModels= new ArrayList<>();

        dataModels.addAll(InstagramAPI.getNonFollowers());

        adapter= new CustomAdapter(dataModels,getContext());

        listView.setAdapter(adapter);
        return view;
    }
}
