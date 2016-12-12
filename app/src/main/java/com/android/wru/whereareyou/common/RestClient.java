package com.android.wru.whereareyou.common;

/**
 * Created by HuyTran1 on 12/6/16.
 */

import android.content.Context;
import android.os.Looper;
import com.loopj.android.http.*;

/**
 * Restful client connecting to backend
 */
public class RestClient
{
    private static final String BASE_URL = "http://104.236.64.241/api/v1/";
    private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    public static AsyncHttpClient syncHttpClient= new SyncHttpClient();

    /**
     * HTTP GET METHOD
     * @param url relative url
     * @param params parameter (query string)
     * @param responseHandler
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        getClient().get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * HTTP POST METHOD
     * @param url relative url
     * @param params request body
     * @param responseHandler
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        getClient().post(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * HTTP PUT METHOD
     * @param url relative url
     * @param params request body
     * @param responseHandler
     */
    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        getClient().put(getAbsoluteUrl(url), params, responseHandler);
    }
    /**
     * Get absolute path
     * @param relativeUrl
     * @return String
     */
    private static String getAbsoluteUrl(String relativeUrl)
    {
        return BASE_URL + relativeUrl;
    }

    public static AsyncHttpClient getClient()
    {
      // Return the synchronous HTTP client when the thread is not prepared
        return Looper.myLooper() == null ? syncHttpClient : asyncHttpClient;
    }

}
