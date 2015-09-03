package com.example.tronyak.qrqver001;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by Yura on 28.07.2015.
 */
public class Qests extends ListFragment {



    private CustomParseQueryAdapter mAdapter;
    public static final String ARG_PAGE = "ARG_PAGE";
    FragmentTransaction fTrans;



    public static Start_page create(int page, String type){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("type", "all");
        Start_page fragment = new Start_page();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.quests_page, container, false);
        mAdapter = new CustomParseQueryAdapter(getActivity());
        setListAdapter(mAdapter);
        mAdapter.loadObjects();

        return rootView;
    }



    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

    public class CustomParseQueryAdapter extends ParseQueryAdapter<Quests_elements> {
        public CustomParseQueryAdapter(Context context){
            super(context, new QueryFactory<Quests_elements>() {
                public ParseQuery create() {
                    ParseQuery query = new ParseQuery("Quest");
                    query.addDescendingOrder("time_begin");
                    query.whereEqualTo("quest_prodaction", true);
                    query.setLimit(3);
                    return query;
                }
            });
        }


        @Override
        public View getItemView(final Quests_elements quest, View v, ViewGroup parent){
            if (v == null){
                v = View.inflate(getContext(), R.layout.quest_layout, null);
            }

            super.getItemView(quest, v, parent);
            AQuery aq = new AQuery(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Snackbar.make(getListView(), quest.getObjectId(), Snackbar.LENGTH_LONG).show();

                    Fragment quest_detail_page = new Quest_detail_page();
                    Bundle bundle = new Bundle();
                    fTrans = getFragmentManager().beginTransaction();
                    fTrans.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
                    fTrans.replace(R.id.container, quest_detail_page);
                    bundle.putString("q_id", quest.getObjectId());
                    quest_detail_page.setArguments(bundle);

                    fTrans.addToBackStack( "tag" ).commit();
                   /*Intent myIntent = new Intent(getContext(), Quest_detail_page.class);
                    myIntent.putExtra("q_id", quest.getObjectId());
                    startActivity(myIntent);*/
                }
            });


            if(quest.getQuest_is_active() == true){
                if(quest.getCheckpoints() != null) {
                    aq.id(R.id.list_quest_checkpoints_number).text(String.valueOf(quest.getCheckpoints().length()));
                    aq.id(R.id.list_checkpoint_count_holder).visible();

                }
                aq.id(R.id.list_quest_started_block).visible().animate(R.anim.bounce);
            }else {
                aq.id(R.id.list_time_to_quest).visible();

                /* date mahinations */

                long s = quest.getQuest_Time_begin().getTime() ;


                Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"),
                        Locale.getDefault());
                long n = c.getTimeInMillis();
                /*SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String n = df.format(c.getTimeInMillis());*/
                long to_time = (s - 21600000) - n;
                SimpleDateFormat df = new SimpleDateFormat("d 'day' HH:mm:ss");
                String z = df.format(to_time);
                if (s >= n) {
                    Log.d("tronyakz date", /*"time of quest = " + s + " now time = " + n*/quest.getQuest_Time_begin() + " to quest = " + z);
                    aq.id(R.id.list_quest_time).text(z);
                }else{
                    aq.id(R.id.list_quest_time).text("This quest is finish");
                    aq.id(R.id.list_text_start).invisible();
                    aq.id(R.id.list_checkpoint_count_holder).invisible();
                }
            }
            aq.id(R.id.list_name_of_quest).text(quest.getQuestName());
            /*aq.id(R.id.news_text).text(quest.getQusetDeskription());*/
            if(quest.getQuest_is_active()){

            }
            aq.id(R.id.list_where_are_quest).text(quest.getLocation_of_quest());
            aq.id(R.id.list_quest_image).image(quest.getQuestPhoto(), true, true, 0, 0, null, 0, AQuery.RATIO_PRESERVE);


            return v;


        }
        @Override
        public View getNextPageView(View v, ViewGroup parent) {
            if (v == null) {
                v = View.inflate(getContext(), R.layout.showmore, null);
            }
            TextView textView = (TextView) v.findViewById(R.id.show_more_text);
            textView.setText("Loaded " + getCount() + " quests. Get more!");
            return v;
        }

    }

}