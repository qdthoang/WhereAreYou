package com.android.wru.whereareyou.common;

import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import org.json.*;

import java.security.Policy;
import java.util.ArrayList;

/**
 * Created by HuyTran1 on 12/6/16.
 */

public class RestClientUsersResource
{
    ArrayList<User> usersList;
    User newUser;

    /**
     * GET to /api/v1/users?cursor=x&limit=y
     * @param cursor
     * @param limit
     * @return ArrayList<User>
     * @throws JSONException
     */
    public ArrayList<User> getAllUsers(int cursor, int limit) throws JSONException
    {
        RequestParams params = new RequestParams();
        params.put("cursor", cursor);
        params.put("limit", limit);
        RestClient.get("users", params, new JsonHttpResponseHandler()
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
                    usersList = new ArrayList<User>();
                    JSONArray users = response.getJSONArray("data");
                    for (int i = 0; i < users.length(); i++) {
                        /**
                         * user : { username, email }
                         */
                        JSONObject tmpUser = users.getJSONObject(i);
                        User user = new User(tmpUser.getString("username"), tmpUser.getString("email"));

                        usersList.add(user);
                    }
                } catch (JSONException ex) {
                    ex.getMessage();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response)
            {
                // Return object, so ignore this
            }
        });

        return usersList;
    }

    /**
     * GET /api/v1/users
     * @return
     * @throws JSONException
     */
    public ArrayList<User> getAllUsers() throws JSONException
    {
        RestClient.get("users", null, new JsonHttpResponseHandler()
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
                    usersList = new ArrayList<User>();
                    JSONArray users = response.getJSONArray("data");
                    for (int i = 0; i < users.length(); i++) {
                        /**
                         * user : { username, email }
                         */
                        JSONObject tmpUser = users.getJSONObject(i);
                        User user = new User(tmpUser.getString("username"), tmpUser.getString("email"));

                        usersList.add(user);
                    }
                } catch (JSONException ex) {
                    ex.getMessage();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response)
            {
                // Return object, so ignore this
            }
        });

        return usersList;
    }

    /**
     * POST /api/v1/users
     * @throws JSONException
     */
    public void createNewUser(String username, String email, String password) throws JSONException
    {
        RequestParams params = new RequestParams();
        params.put("username", username);
        params.put("email", email);
        params.put("password", password);
        RestClient.post("users", params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                /**
                 * On success,
                 * statusCode = 201 Created
                 * response --> new user Object
                 * save to newUser instance variable
                 */
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                /**
                 * On failure
                 * statusCode = 422 Unprocessable Entity
                 * response -> error message object
                 */
            }
        });
    }
}
