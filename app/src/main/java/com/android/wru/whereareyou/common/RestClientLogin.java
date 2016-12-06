package com.android.wru.whereareyou.common;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by HuyTran1 on 12/6/16.
 */

public class RestClientLogin
{
    /**
     * POST to /api/v1/oauth/access_token
     * @throws JSONException
     */
    public String getAccessToken() throws JSONException
    {
        RestClient.get("oauth/access_token", null, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
            }
        });

        return "";
    }


}
