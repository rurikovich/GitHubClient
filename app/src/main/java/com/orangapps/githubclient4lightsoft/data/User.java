package com.orangapps.githubclient4lightsoft.data;

import android.graphics.Bitmap;

/**
 * Created by rurik on 27.09.14.
 */
public class User {
    private String login;
    private String avatar_url;
    private String repos_url;
    private Bitmap img;

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

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }
}
