package com.android.wru.whereareyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.wru.whereareyou.common.RestClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
public class GetFriendLocation extends AppCompatActivity
        implements OnMapReadyCallback {
    private LatLng friendLocation;
    private String token;
    private String username;
    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 15;


    private void initializeData() {
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
                    mMap.addMarker(new MarkerOptions().position(friendLocation)
                            .title("Marker in frined location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            friendLocation, DEFAULT_ZOOM));


                    Log.d("statusCode", String.valueOf(statusCode));
                    Log.d("username", username);
                    Log.d("latlng", friendLocation.toString());
                } catch (JSONException ex) {
                    ex.getMessage();
                }
            }


            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("HELLO adasdsa", "ffdsfdsf");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                Log.d("HELLO WOhfghrgdghRDL", "ffdsfdsf");
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
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_get_friend_location);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);
        final Intent intent = getIntent();
        token = intent.getStringExtra(LoginActivity.ACCESS_TOKEN);
        username = intent.getStringExtra(FriendListActivity.USERNAME);
        Log.d("init", "start init");
        Log.d("end init", "dsf");
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user receives a prompt to install
     * Play services inside the SupportMapFragment. The API invokes this method after the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        initializeData();
        if (friendLocation == null){
            friendLocation = new LatLng(33, 60);
        }

        Log.d("latlng", friendLocation.toString());
    }

}
