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

public class AcceptFriendActivity extends AppCompatActivity {
    static final String ACCESS_TOKEN = "access_token";
    private String token;

    ListView lv;
    private ArrayList<User> usersList = new ArrayList<>();
    private ArrayAdapter<User> adapter;

    private void initializeData() {
        RestClient.getClient().addHeader("Authorization", "Bearer " + token);
        RestClient.get("followers/request", null, new JsonHttpResponseHandler()
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
        setContentView(R.layout.activity_accept_friend);

        Intent intent = getIntent();
        token = intent.getStringExtra(LoginActivity.ACCESS_TOKEN);
        initializeData();
        Log.d("onCreate", "token: " + token);

        lv = (ListView) findViewById(R.id.acceptFriendList);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, usersList);

        // Assign adapter to ListView
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                acceptFriend(usersList.get(position).getUsername());
            }
        });
    }

    public void acceptFriend(String username) {
        RestClient.getClient().addHeader("Authorization", "Bearer " + token);
        RestClient.put("followers/request/" + username + "/accept", null, new JsonHttpResponseHandler()
        {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /**
                 * On success,
                 * statusCode = 200 OK
                 * response --> new Token object
                 * save to newUser instance variable
                 */
                Toast.makeText(AcceptFriendActivity.this, "Accept Friend Request Successfully", Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                /**
                 * On failure
                 * statusCode = 401 Unauthorized
                 * response -> show error
                 */
                Log.d("postAccessToken", "get token failed");
                Log.d("postAccessToken", "status code: " + statusCode);
                Toast.makeText(AcceptFriendActivity.this, "Fail to Accept Friend Request", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void back(View v) {
        Intent intent = new Intent(AcceptFriendActivity.this, FriendListActivity.class);
        intent.putExtra(ACCESS_TOKEN, token);
        startActivity(intent);
    }
}
