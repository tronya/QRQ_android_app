package com.example.tronyak.qrqver001;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.json.JSONArray;

import java.util.Date;

/**
 * Created by Yura on 28.07.2015.
 */

@ParseClassName("Quest")
public class Quests_elements extends ParseObject {


    public Date getQuest_Time_begin(){ return getDate("time_begin"); }
    public void setQuest_Time_begin(Date time_begin){
        put("time_begin", time_begin);
    }

    public String getQuestName(){ return getString("quest_name"); }
    public void setQuestName(String questName){
        put("quest_name", questName);
    }

    public String getLocation_of_quest(){
        return getString("quest_location");
    }
    public void setLocation_of_quest(String quest_location){
        put("quest_location", quest_location);
    }

    public Boolean getQuest_is_active(){
        return getBoolean("quest_active");
    }
    public void setQuest_is_active(Boolean quest_active){
        put("quest_active", quest_active);
    }

    public String getQuestDeskription(){
        return getString("description");
    }
    public void setQuestDescription(String description){
        put("description", description);
    }


    public String getQuestPhoto(){
        return getString("quest_big_photo");
    }
    public void setQuestPhoto(String quest_Photo){
        put("questPhoto", quest_Photo);
    }




    public JSONArray getCheckpoints(){
        return getJSONArray("points_of_quest");
    }
    public void setCheckpoints(JSONArray points_of_quest){
        put("points_of_quest", points_of_quest);
    }
}