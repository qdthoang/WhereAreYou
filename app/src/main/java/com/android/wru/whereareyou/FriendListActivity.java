package com.android.wru.whereareyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.wru.whereareyou.common.RestClient;
import com.android.wru.whereareyou.common.User;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FriendListActivity extends AppCompatActivity {
    static final String ACCESS_TOKEN = "access_token";
    static final String USERNAME = "username";

    private String token;
    private String username;
    private LatLng friendLocation;

    ListView lv;
    private ArrayList<User> usersList = new ArrayList<>();
    private ArrayAdapter<User> adapter;

    private void initializeData() {
        Log.d("test", "testest");
        RestClient.getClient().addHeader("Authorization", "Bearer " + token);
        RestClient.get("following", null, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try {
                    /**
                     * { data [ user, user ... user], meta { } }
                     * -->
                     * [user , user , user ... ]
                     */
                    usersList.clear();
                    JSONArray users = response.getJSONArray("data");
                    for (int i = 0; i < users.length(); i++) {
                        /**
                         * user : { username, email }
                         */
                        JSONObject tmpUser = users.getJSONObject(i);
                        User user = new User(tmpUser.getString("username"), tmpUser.getString("email"));

                        usersList.add(user);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException ex) {
                    ex.getMessage();
                }
            }
        });
    }

    private void init() {
        Log.d("Username init", username);
        Log.d("Token", token);
        RestClient.getClient().addHeader("Authorization", "Bearer " + token);
        RestClient.get("location/" + username, null, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try {
                    /**
                     * { data [ user, user ... user], meta { } }
                     * -->
                     * [user , user , user ... ]
                     */
                    Log.d("HELLO WORDL", "success location");


                    friendLocation = new LatLng(response.getDouble("latitude"), response.getDouble("longitude"));
                    Log.d("statusCode", String.valueOf(statusCode));
                    Log.d("username", username);
                    Log.d("latlng", friendLocation.toString());
                } catch (JSONException ex) {
                    ex.getMessage();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("HELwerewrLO WORDL", "ffdsfdsf");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("HEytrytryLLO WORDL", "ffdsfdsf");

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("H4324234ELLO WORDL", "ffdsfdsf");

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        final Intent intent = getIntent();
        token = intent.getStringExtra(LoginActivity.ACCESS_TOKEN);
        initializeData();
        Log.d("onCreate", "token: " + token);

        lv = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, usersList);

        // Assign adapter to ListView
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                username = usersList.get(position).getUsername();
                Log.d("FriendList username:",username);
                Intent intent = new Intent(FriendListActivity.this, GetFriendLocation.class);
                intent.putExtra(ACCESS_TOKEN, token);
                intent.putExtra(USERNAME, username);
                startActivity(intent);
            }
        });
    }

    public void addFriend(View v) {
        Intent intent = new Intent(FriendListActivity.this, AddFriendActivity.class);
        intent.putExtra(ACCESS_TOKEN, token);
        startActivity(intent);
    }

    public void acceptFriend(View v) {
        Intent intent = new Intent(FriendListActivity.this, AcceptFriendActivity.class);
        intent.putExtra(ACCESS_TOKEN, token);
        startActivity(intent);
    }

    public void updateLocation(View v) {
        Intent intent = new Intent(FriendListActivity.this, UpdateLocationActivity.class);
        intent.putExtra(ACCESS_TOKEN, token);
        startActivity(intent);
    }

}
