package com.example.tronyak.qrqver001;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Yura on 05.08.2015.
 */
public class Quest_detail_page extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState){
        Bundle bundle = getArguments();
        String q_id = bundle.getString("q_id");
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.detail_quest, container, false);
        final AQuery aq = new AQuery(container);
        Bundle bundle = getArguments();
        String q_id = bundle.getString("q_id");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Quest");
        query.whereEqualTo("objectId", q_id);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Log.d("score", "The getFirst request failed.");
                } else {
                    Log.d("score", object.getString("description") + "Retrieved the object.");
                    aq.id(R.id.deteil_quest_image).image(object.getString("quest_big_photo"), true, true, 0, 0, null, 0, AQuery.RATIO_PRESERVE);
                    aq.id(R.id.detail_quest_description).text(object.getString("description"));


                    if (object.getBoolean("quest_active") == true) {
                        if (object.getJSONArray("points_of_quest") != null) {
                            aq.id(R.id.detail_quest_checkpoints_number).text(object.getJSONArray("points_of_quest").length());
                            aq.id(R.id.detail_checkpoint_count_holder).visible();

                        }
                        aq.id(R.id.detail_quest_started_block).visible().animate(R.anim.bounce);
                    } else {
                        aq.id(R.id.detail_time_to_quest).visible();

                /* date mahinations */

                        long s = object.getDate("time_begin").getTime();


                        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                                Locale.getDefault());
                        long n = c.getTimeInMillis();
                /*SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String n = df.format(c.getTimeInMillis());*/
                        long to_time = (s - 21600000) - n;
                        SimpleDateFormat df = new SimpleDateFormat("d 'day' HH:mm:ss");
                        String z = df.format(to_time);
                        if (s >= n) {
                            Log.d("tronyakz date", /*"time of quest = " + s + " now time = " + n*/object.getDate("time_begin") + " to quest = " + z);
                            aq.id(R.id.deteil_quest_time).text(z);
                        } else {
                            aq.id(R.id.deteil_quest_time).text("This quest is finish");
                            aq.id(R.id.deteil_text_start).invisible();
                            aq.id(R.id.detail_checkpoint_count_holder).invisible();
                        }
                        aq.id(R.id.deteil_name_of_quest).text(object.getString("quest_name"));
                        aq.id(R.id.deteil_where_are_quest).text(object.getString("quest_location"));
                    }

                }
            }
        });
        return rootView;
    }



    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

/*
    private void setPalette() {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int primaryDark = getResources().getColor(R.color.ripple_material_dark);
                int primary = getResources().getColor(R.color.ripple_material_light);
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkVibrantColor(primaryDark));
            }
        });

    }*/
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pic1) {
            image.setImageResource(R.drawable.pic);
            setPalette();
            return true;
        }
        if (id == R.id.pic2) {
            image.setImageResource(R.drawable.pic2);
            setPalette();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
