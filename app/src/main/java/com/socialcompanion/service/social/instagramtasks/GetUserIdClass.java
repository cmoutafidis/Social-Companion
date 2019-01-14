package com.socialcompanion.service.social.instagramtasks;

import android.os.AsyncTask;

import com.socialcompanion.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class GetUserIdClass extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... user) {
        try {
            HttpGet httppost = new HttpGet("https://www.instagram.com/" + user[0] + "/?__a=1");
            HttpClient httpclient = new DefaultHttpClient();
            try {
                HttpResponse response = httpclient.execute(httppost);

                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String responseString = EntityUtils.toString(entity);
                    JSONObject obj = new JSONObject(responseString);
                    return obj.getJSONObject("graphql").getJSONObject("user").getString("id");
                }
            } catch (RuntimeException | JSONException e){
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

    }
}