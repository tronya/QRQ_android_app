package com.example.tronyak.qrqver001;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Yura on 02.07.2015.
 */
public class LoginActivity extends Activity {

    private Dialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loginlayout);
        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            // Go to the user info activity
            showUserDetailsActivity();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    public void onLoginClick(View v) {
        progressDialog = ProgressDialog.show(LoginActivity.this, "", "Logging in...", true);

        List<String> permissions = Arrays.asList("public_profile", "email","user_photos");

        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, permissions, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                progressDialog.dismiss();
                if (user == null) {
                    Log.d("tronyaks outh", "Uh oh. The user cancelled the Facebook login.");
                } else if (user.isNew()) {
                    Log.d("tronyaks outh", "User signed up and logged in through Facebook!");
                    makeMe_new_user_Request();
                } else {
                    Log.d("tronyaks outh", "User logged in through Facebook!");
                    showUserDetailsActivity();
                }
            }
        });
    }


    private void makeMe_new_user_Request() {




        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject jsonObject, GraphResponse graphResponse) {
                        if (jsonObject != null) {
                            Log.d("tronyak zzz",jsonObject.toString());
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            try {
                                Long fb_id = jsonObject.getLong("id");
                                String name =  jsonObject.getString("name");
                                if (jsonObject.getString("gender") != null)
                                    currentUser.put("gender", jsonObject.getString("gender"));
                                if (jsonObject.getString("email") != null)
                                    currentUser.put("email", jsonObject.getString("email"));
                                if (jsonObject.getString("first_name") != null)
                                    currentUser.put("first_name", jsonObject.getString("first_name"));
                                if (jsonObject.getString("last_name") != null)
                                    currentUser.put("last_name", jsonObject.getString("last_name"));
                                if (jsonObject.getString("link") != null)
                                    currentUser.put("link_site", jsonObject.getString("link"));
                                if (jsonObject.getString("locale") != null)
                                    currentUser.put("locale", jsonObject.getString("locale"));
                                if (jsonObject.getString("timezone") != null)
                                    currentUser.put("timezone", jsonObject.getString("timezone"));
                                currentUser.put("facebook_id", fb_id.toString());
                                currentUser.put("username", name);

                                /*currentUser.saveInBackground();*/

                                currentUser.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        showUserDetailsActivity();
                                    }
                                });
                            } catch (JSONException e) {
                                Log.d("tronyak driod autz",
                                        "Error parsing returned user data. " + e);
                            }
                        }
                    }
                });

        request.executeAsync();



        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/picture",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        Log.d("tronyak avar", response.toString());
                        ParseUser currentUser = ParseUser.getCurrentUser();
                        /*currentUser.put("smoll_avatar", response.get.getString("url");*/
                    }
                }
        ).executeAsync();
    }

    private void showUserDetailsActivity() {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        Log.d("tronyak outh", "User show activity");
        startActivity(intent);
    }
}