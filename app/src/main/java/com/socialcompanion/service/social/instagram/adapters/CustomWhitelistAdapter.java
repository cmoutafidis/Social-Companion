package com.socialcompanion.service.social.instagram.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.socialcompanion.R;
import com.socialcompanion.service.social.instagram.InstagramUserObject;

import java.util.ArrayList;
import java.util.HashSet;

public class CustomWhitelistAdapter extends ArrayAdapter<InstagramUserObject> implements View.OnClickListener{

    private ArrayList<InstagramUserObject> dataSet;
    Context mContext;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
        ImageView action;
    }

    public CustomWhitelistAdapter(ArrayList<InstagramUserObject> data, Context context) {
        super(context, R.layout.fragment_user_whitelist, data);
        this.dataSet = data;
        this.mContext=context;

        sharedPref = getContext().getSharedPreferences("socialAccess", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        InstagramUserObject dataModel=(InstagramUserObject)object;

        HashSet<String> hashSet = (HashSet<String>) sharedPref.getStringSet("whitelist", new HashSet<String>());

        if(!sharedPref.getStringSet("whitelist", new HashSet<String>()).contains(dataModel.getUsername())){
            hashSet.add(dataModel.getUsername());
            ((ImageView)v.findViewById(R.id.imageView3)).setImageResource(R.drawable.remove_green);
        }
        else{
            hashSet.remove(dataModel.getUsername());
            ((ImageView)v.findViewById(R.id.imageView3)).setImageResource(R.drawable.add_circle_big);
        }

        editor.remove("whitelist");
        editor.apply();
        editor.putStringSet("whitelist", hashSet);
        editor.apply();
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        InstagramUserObject dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_user_whitelist, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.textView4);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.imageView2);
            viewHolder.action = (ImageView) convertView.findViewById(R.id.imageView3);
            result=convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getUsername());
        viewHolder.info.setImageBitmap(dataModel.getBitmapImage());

        if(sharedPref.getStringSet("whitelist", new HashSet<String>()).contains(dataModel.getUsername())){
            viewHolder.action.setImageResource(R.drawable.remove_green);
        }
        else{
            viewHolder.action.setImageResource(R.drawable.add_circle_big);
        }

        viewHolder.action.setOnClickListener(this);
        viewHolder.action.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}