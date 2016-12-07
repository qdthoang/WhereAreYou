package com.android.wru.whereareyou;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.wru.whereareyou.common.RestClient;
import com.android.wru.whereareyou.common.RestClientLogin;
import com.android.wru.whereareyou.common.RestClientUsersResource;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.*;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = "LoginActivity";

    private String loginUsername;
    private String loginPassword;
    private String token;
    static final String ACCESS_TOKEN = "access_token";

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(ACCESS_TOKEN, token);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            new RestClientUsersResource().getAllUsers();
        } catch (JSONException ex) {
            ex.getMessage();
        }
        setContentView(R.layout.activity_login);

        if (savedInstanceState != null) {
            token = savedInstanceState.getString(ACCESS_TOKEN);
        }

        EditText mLoginUsername = (EditText) findViewById(R.id.login_username);
        mLoginUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginUsername = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        EditText mLoginPassword = (EditText) findViewById(R.id.login_password);
        mLoginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loginPassword = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void login (View v) throws JSONException{
        RequestParams params = new RequestParams();
        params.put("grant_type", "password");
        params.put("client_id", 0);
        params.put("client_secret", "secret0");
        params.put("username", loginUsername);
        params.put("password", loginPassword);
        Log.d("postAccessToken", "username: " + loginUsername);
        Log.d("postAccessToken", "password: " + loginPassword);
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
                    Log.d("postAccessToken", "status code: " + statusCode);
                    Intent intent = new Intent(LoginActivity.this, UpdateLocationActivity.class);
                    intent.putExtra(ACCESS_TOKEN, token);
                    startActivity(intent);
                } catch (JSONException ex) {
                    ex.getMessage();
                }
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
                Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signUp (View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public String getToken () {
        return token;
    }
}