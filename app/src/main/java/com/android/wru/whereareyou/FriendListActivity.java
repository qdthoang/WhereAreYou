package com.android.wru.whereareyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class FriendListActivity extends AppCompatActivity {
    static final String ACCESS_TOKEN = "access_token";
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Intent intent = getIntent();
        token = intent.getStringExtra(LoginActivity.ACCESS_TOKEN);

        Log.d("onCreate", "token: " + token);
    }
}
