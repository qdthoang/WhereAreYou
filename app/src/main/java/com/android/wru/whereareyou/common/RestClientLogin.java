package com.android.wru.whereareyou.common;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by HuyTran1 on 12/6/16.
 */

public class RestClientLogin {

    String token;

    /**
     * POST to /api/v1/oauth/access_token
     *
     * @throws JSONException
     */
    public void postAccessToken(Context context, String username, String password) throws JSONException {
        //int code;
        RequestParams params = new RequestParams();
        params.put("grant_type", "password");
        params.put("client_id", 0);
        params.put("client_secret", "secret0");
        params.put("username", username);
        params.put("password", password);
        Log.d("postAccessToken", "username: " + username);
        Log.d("postAccessToken", "password: " + password);
        RestClient.post("oauth/access_token", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /**
                 * On success,
                 * statusCode = 200 OK
                 * response --> new Token object
                 * save to newUser instance variable
                 */
                try {
                    token = response.getString("access_token");
                    Log.d("postAccessToken", "get token success: " + token);
                    Log.d("postAccessToken", "status code pre: " + statusCode);
                } catch (JSONException ex) {
                    ex.getMessage();
                }
                Log.d("postAccessToken", "status code pre2: " + statusCode);
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
            }
        });
    }

    public String getToken() {
        return token;
    }

}
