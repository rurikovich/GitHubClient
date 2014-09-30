package com.orangapps.githubclient4lightsoft.data;

import com.orangapps.githubclient4lightsoft.githubApi.AsyncRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.orangapps.githubclient4lightsoft.utils.httpUtils.processStrToArray;
import static com.orangapps.githubclient4lightsoft.utils.httpUtils.sendGetRequest;

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


    public synchronized void fetchNewUsers() throws ExecutionException, InterruptedException, JSONException {
        String users_str = new AsyncRequest(GET_USERS_URL + "?since=" + currentPage).execute().get();
        String[] usersArray = processStrToArray(users_str);
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
        String[] usersArray = processStrToArray(users_str);
        for (String userStr : usersArray) {
            JSONObject userJson = new JSONObject(userStr);
            User user = new User(userJson.getString(LOGIN), userJson.getString(AVATAR_URL), userJson.getString(REPOS_URL));
            users.add(user);
        }
        currentPage += PAGE_STEP + 1;
    }


    public void init() throws InterruptedException, ExecutionException, JSONException {
        fetchNewUsers();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void clear(){
        getUsers().clear();
        currentPage = 0;
    }


}
