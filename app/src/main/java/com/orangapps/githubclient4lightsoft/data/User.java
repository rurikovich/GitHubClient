package com.orangapps.githubclient4lightsoft.data;

/**
 * Created by rurik on 27.09.14.
 */
public class User {
    private String login;
    private String avatar_url;
    private String repos_url;

    public User(String login, String avatar_url, String repos_url) {
        this.login = login;
        this.avatar_url = avatar_url;
        this.repos_url = repos_url;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String toString() {
        return login;
    }
}
