package com.orangapps.githubclient4lightsoft.githubApi;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by rurik on 27.09.14.
 */
public class AsyncRequest extends AsyncTask<Void, Void, String> {


    private String url;

    public AsyncRequest(String url) {
        this.url = url;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            return sendGetRequest(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String sendGetRequest(String url) throws JSONException {
        String response_body_str = "";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response;
        try {
            response = client.execute(request);
            response_body_str = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response_body_str;

    }
}

