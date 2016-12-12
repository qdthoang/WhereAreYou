package com.android.wru.whereareyou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.wru.whereareyou.common.RestClient;
import com.android.wru.whereareyou.common.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FriendListActivity extends AppCompatActivity {
    static final String ACCESS_TOKEN = "access_token";
    private String token;

    ListView lv;
    private ArrayList<User> usersList = new ArrayList<>();
    private ArrayAdapter<User> adapter;

    private void initalizeData() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        Intent intent = getIntent();
        token = intent.getStringExtra(LoginActivity.ACCESS_TOKEN);
        initalizeData();
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
                Intent intent = new Intent(FriendListActivity.this, UpdateLocationActivity.class);
                intent.putExtra(ACCESS_TOKEN, token);
                startActivity(intent);
            }
        });
    }


}
