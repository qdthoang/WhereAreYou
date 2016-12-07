package com.android.wru.whereareyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.wru.whereareyou.common.RestClient;
import com.android.wru.whereareyou.common.RestClientUsersResource;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SignupActivity extends AppCompatActivity {
    private static String TAG = "SignupActivity";

    private String signupUsername;
    private String signupPassword;
    private String signupEmail;
    static final String ACCESS_TOKEN = "access_token";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            new RestClientUsersResource().getAllUsers();
        } catch (JSONException ex) {
            ex.getMessage();
        }
        setContentView(R.layout.activity_signup);

        EditText mSignupUsername = (EditText) findViewById(R.id.signup_username);
        mSignupUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signupUsername = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        EditText mSignupPassword = (EditText) findViewById(R.id.signup_password);
        mSignupPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signupPassword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        EditText mSignupEmail = (EditText) findViewById(R.id.signup_email);
        mSignupEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                signupEmail = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void createAccount(View v) {
        RequestParams params = new RequestParams();
        params.put("username", signupUsername);
        params.put("password", signupPassword);
        params.put("email", signupEmail);
        Log.d("postAccessToken", "username: " + signupUsername);
        Log.d("postAccessToken", "password: " + signupPassword);
        Log.d("postAccessToken", "email: " + signupEmail);
        RestClient.post("users", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /**
                 * On success,
                 * statusCode = 201 created
                 * response --> new Token object
                 * save to newUser instance variable
                 */
                Log.d("postAccessToken", "status code: " + statusCode);
                Toast.makeText(SignupActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
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
                Toast.makeText(SignupActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
