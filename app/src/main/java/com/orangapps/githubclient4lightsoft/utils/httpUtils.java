package com.orangapps.githubclient4lightsoft.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by rurik on 30.09.14.
 */
public class httpUtils {

        public static String[] processStrToArray(String users_str) {
        users_str = users_str.substring(1, users_str.length() - 1);
        users_str = users_str.replace("},{", "},,{");
        return users_str.split(",,");
    }

    public static String sendGetRequest(String url) throws JSONException {
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
