package com.example.tronyak.qrqver001;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseUser;

/**
 * Created by Yura on 03.08.2015.
 */
@ParseClassName("_User")
public class Users_elements extends ParseUser {

    public String getFacebookId(){ return getString("facebook_id"); }
    public void setFacebookId(String facebookId){
        put("time_begin", facebookId);
    }


    public String getUserGender(){ return getString("gender"); }
    public void setUserGender(String userGender){
        put("gender", userGender);
    }

    public String getUserLinkSite(){ return getString("link_site"); }
    public void setUserLinkSite(String userLinkSite){
        put("link_site", userLinkSite);
    }

    public String getUserFirstName(){ return getString("first_name"); }
    public void setUserFirstName(String firstName){
        put("first_name", firstName);
    }

    public String getUserLastName(){ return getString("last_name"); }
    public void setUserLastName(String lastName){
        put("last_name", lastName);
    }
    public String getUserSmallPicture(){ return getString("smoll_avatar"); }
    public void setUserSmallPicture(String smallPicture){
        put("smoll_avatar", smallPicture);
    }
    public ParseGeoPoint getUserParseGeoPoint(){ return getParseGeoPoint("my_position"); }
    public void setUserParseGeoPoint(Double latitude, Double longitude) {
        put("my_position", new ParseGeoPoint(latitude, longitude));
    }


}
