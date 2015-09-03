package com.example.tronyak.qrqver001;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.androidquery.AQuery;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

public class UserDetailsActivity extends Activity {

    AQuery aq = new AQuery(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.userdetails);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && currentUser.isAuthenticated()) {
            makeMeRequest();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            updateViewsWithProfileInfo();
        } else {
            startLoginActivity();
        }
    }

    private void makeMeRequest() {
        ParseUser currentUser = ParseUser.getCurrentUser();

                if (currentUser.has("username")) {
                    aq.id(R.id.user_detail_name).text(currentUser.getString("username"));
                } else {
                    aq.id(R.id.user_detail_name).text("Your Name");
                }

                if (currentUser.has("gender")) {
                    aq.id(R.id.userGender).text(currentUser.getString("gender"));
                } else {
                    aq.id(R.id.userGender).text("");
                }
                if (currentUser.has("first_name")) {
                    aq.id(R.id.user_Name).text(currentUser.getString("first_name"));
                } else {
                    aq.id(R.id.user_Name).text("");
                }
                if (currentUser.has("last_name")) {
                    aq.id(R.id.user_LastName).text(currentUser.getString("last_name"));
                } else {
                    aq.id(R.id.user_LastName).text("");
                }
                if (currentUser.has("email")) {
                    aq.id(R.id.userEmail).text(currentUser.getString("email"));
                } else {
                    aq.id(R.id.userEmail).text("");
                }
                if (currentUser.has("my_position")) {
                    ParseGeoPoint cur_pozi = (ParseGeoPoint) currentUser.get("my_position");
                    aq.id(R.id.det_user_coordinats).text(cur_pozi.getLatitude() + " " + cur_pozi.getLongitude());
                } else {
                    aq.id(R.id.det_user_coordinats).text("");
                }
                if (currentUser.has("link_site")) {
                    aq.id(R.id.det_user_serlink).text(currentUser.getString("link_site"));
                } else {
                    aq.id(R.id.det_user_serlink).text("");
                }
                if (currentUser.has("big_avatar")) {
                    aq.id(R.id.detail_user_image).image(currentUser.getString("big_avatar"), true, true, 0, 0, null, 0, 9.0f / 16.0f);
                } else {
                    aq.id(R.id.detail_user_image).image(currentUser.getString("smoll_avatar"), true, true, 0, 0, null, 0, 9.0f / 16.0f);
                }
            }

    private void updateViewsWithProfileInfo() {

    }

    public void onLogoutClick(View v) {
        logout();
    }

    private void logout() {
        // Log the user out
        ParseUser.logOut();

        // Go to the login view
        startLoginActivity();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
