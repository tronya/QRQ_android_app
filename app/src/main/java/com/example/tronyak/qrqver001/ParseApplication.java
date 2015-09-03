package com.example.tronyak.qrqver001;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

/**
 * Created by Yura on 16.04.2015.
 */public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
        ParseObject.registerSubclass(News_elements.class);
        ParseObject.registerSubclass(Quests_elements.class);
        ParseObject.registerSubclass(Users_elements.class);


        Parse.initialize(this, "yAErXXQxCTY1ZL157EUEx8rGIbIVrJksDmt3oNai", "ApdfWDsMcXXShuRkPdXH73dE9fVIvLzVrnai6eOZ");
        /*ParseUser.enableAutomaticUser();*/
        ParseFacebookUtils.initialize(this);
        ParseACL defaultACL = new ParseACL();

        defaultACL.setPublicReadAccess(true);

        ParseACL.setDefaultACL(defaultACL, true);
        isOnline();




    }
    protected boolean isOnline() {
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            Toast.makeText(getApplicationContext(), "You have no internet", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(getApplicationContext(),"Everything all right", Toast.LENGTH_LONG).show();
        }

        return false;
    }


}