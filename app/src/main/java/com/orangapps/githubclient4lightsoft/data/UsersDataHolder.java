package com.orangapps.githubclient4lightsoft.data;

import com.orangapps.githubclient4lightsoft.githubApi.AsyncRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by rurik on 27.09.14.
 */
public class UsersDataHolder {
    public static final String GET_USERS_URL = "https://api.github.com/users";
    public static final String LOGIN = "login";
    public static final String AVATAR_URL = "avatar_url";
    public static final String REPOS_URL = "repos_url";
    public static final int PAGE_STEP = 100;


    private ArrayList<User> users = new ArrayList<User>();
    private int currentPage = 0;
    private int currentUserNumber = 21;
    private int usersStep = 1;


    public synchronized void fetchNewUsers() throws ExecutionException, InterruptedException, JSONException {
        String users_str = new AsyncRequest(GET_USERS_URL + "?since=" + currentPage).execute().get();
        users_str = users_str.substring(1, users_str.length() - 1);
        users_str = users_str.replace("},{", "},,{");
        String[] usersArray = users_str.split(",,");
        for (String userStr : usersArray) {
            JSONObject userJson = new JSONObject(userStr);
            User user = new User(userJson.getString(LOGIN), userJson.getString(AVATAR_URL), userJson.getString(REPOS_URL));
            users.add(user);
        }
        currentPage += PAGE_STEP;
    }

    public synchronized void fetchNewUsersNotAsync() throws ExecutionException, InterruptedException, JSONException {
        String url = GET_USERS_URL + "?since=" + currentPage;
        String users_str = sendGetRequest(url);
        users_str = users_str.substring(1, users_str.length() - 1);
        users_str = users_str.replace("},{", "},,{");
        String[] usersArray = users_str.split(",,");
        for (String userStr : usersArray) {
            JSONObject userJson = new JSONObject(userStr);
            User user = new User(userJson.getString(LOGIN), userJson.getString(AVATAR_URL), userJson.getString(REPOS_URL));
            users.add(user);
        }
        currentPage += PAGE_STEP + 1;
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

    public void init() throws InterruptedException, ExecutionException, JSONException {
        fetchNewUsers();
    }

    public ArrayList<User> getUsers() {
        return users;
    }


}
