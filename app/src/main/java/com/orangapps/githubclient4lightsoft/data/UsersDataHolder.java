package com.orangapps.githubclient4lightsoft.data;

import com.orangapps.githubclient4lightsoft.githubApi.AsyncRequest;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void fetchNewUsers() throws ExecutionException, InterruptedException, JSONException {
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


    public void init() throws InterruptedException, ExecutionException, JSONException {
        fetchNewUsers();
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
