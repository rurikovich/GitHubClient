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

    public String getRepos_url() {
        return repos_url;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!avatar_url.equals(user.avatar_url)) return false;
        if (img != null ? !img.equals(user.img) : user.img != null) return false;
        if (!login.equals(user.login)) return false;
        if (!repos_url.equals(user.repos_url)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + avatar_url.hashCode();
        result = 31 * result + repos_url.hashCode();
        result = 31 * result + (img != null ? img.hashCode() : 0);
        return result;
    }
}
