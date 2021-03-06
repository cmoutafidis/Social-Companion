package com.socialcompanion.menufragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.socialcompanion.MainActivity;
import com.socialcompanion.R;
import com.socialcompanion.service.social.instagram.InstagramAPI;
import com.socialcompanion.service.social.instagram.InstagramUserObject;
import com.socialcompanion.service.social.instagram.adapters.CustomBlacklistAdapter;
import com.socialcompanion.service.social.instagram.adapters.CustomWhitelistAdapter;

import java.util.ArrayList;

public class BlacklistFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    SwipeRefreshLayout swipeLayout;
    ArrayList<InstagramUserObject> dataModels;
    ListView listView;
    private static CustomBlacklistAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blacklist, container, false);

        ((MainActivity)getActivity()).setActionBarTitle("Blacklist");

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.pullToRefresh).findViewById(R.id.pullToRefresh2);
        swipeLayout.setOnRefreshListener(this);

        listView = view.findViewById(R.id.list);
        dataModels= new ArrayList<>();

        dataModels.addAll(InstagramAPI.getNonFollowing());

        adapter= new CustomBlacklistAdapter(dataModels,getContext());

        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onRefresh() {
        View view = this.getView();

        MainActivity.updateUserInformation();
        dataModels= new ArrayList<>();

        dataModels.addAll(InstagramAPI.getNonFollowing());

        adapter= new CustomBlacklistAdapter(dataModels,getContext());

        listView.setAdapter(adapter);
        swipeLayout.setRefreshing(false);
    }
}
