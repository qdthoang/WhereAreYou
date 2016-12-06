package com.android.wru.whereareyou.common;

import java.util.ArrayList;

/**
 * Created by HuyTran1 on 12/6/16.
 */

public class User
{
    private String username;
    private String email;
    private ArrayList<User> followings;


    public User(String username, String email)
    {
        this.username = username;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addFollowings(User user)
    {
        this.followings.add(user);
    }
}
