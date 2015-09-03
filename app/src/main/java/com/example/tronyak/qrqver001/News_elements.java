package com.example.tronyak.qrqver001;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("news")
public class News_elements extends ParseObject {


    public String getTitle(){ return getString("title_news"); }
    public void setTitle(String title){
        put("title_news", title);
    }

    public String getCategory(){ return getString("category"); }
    public void setCategory(String category){
        put("category", category);
    }

    public String getShortText(){
        return getString("text_news");
    }
    public void setShortText(String short_text){
        put("text_news", short_text);
    }


    public Integer getViews_of_News(){
        return getInt("views");
    }
    public void setViews_of_News(Integer news_views){
        put("views", news_views);
    }



    public String getPhoto_url(){
        return getString("photo_src");
    }
    public void setPhoto_url(String url_news){
        put("photo_src", url_news);
    }
}