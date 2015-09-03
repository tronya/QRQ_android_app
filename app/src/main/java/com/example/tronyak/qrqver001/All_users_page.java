package com.example.tronyak.qrqver001;

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
import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Yura on 03.08.2015.
 */
public class All_users_page extends ListFragment {


    private CustomParseQueryAdapter mAdapter;
    public static final String ARG_PAGE = "ARG_PAGE";


    public static All_users_page create(int page, String type) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("type", "all");
        All_users_page fragment = new All_users_page();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_index, container, false);
        mAdapter = new CustomParseQueryAdapter(getActivity());
        setListAdapter(mAdapter);
        mAdapter.loadObjects();
        Log.d("tronyakz userz_page", "loading");



        mAdapter.addOnQueryLoadListener(new ParseQueryAdapter.OnQueryLoadListener<Users_elements>() {
            public void onLoading() {
                Log.d("tronyakz userz", "loading");
            }

            @Override
            public void onLoaded(List<Users_elements> list, Exception e) {
                Log.d("tronyakz userz","loaded");
                Snackbar.make(getView(), "We load all Users", Snackbar.LENGTH_LONG).show();
            }

        });

        return rootView;

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public class CustomParseQueryAdapter extends ParseQueryAdapter<Users_elements> {
        public CustomParseQueryAdapter(Context context) {
            super(context, new QueryFactory<Users_elements>() {
                public ParseQuery create() {
                    /*ParseQuery query = new ParseQuery("User");*/
                   /* ParseQuery<ParseObject> query = ParseQuery.getQuery("User");*/
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.addDescendingOrder("createdAt");
                    query.setLimit(5);
                    return query;
                }
            });
        }


        @Override
        public View getItemView(final Users_elements news, View v, ViewGroup parent) {
            if (v == null) {
                v = View.inflate(getContext(), R.layout.users_items, null);
            }

            super.getItemView(news, v, parent);
            AQuery aq = new AQuery(v);
            ParseGeoPoint mypozi =  new ParseGeoPoint(48.6785738, 26.5905205);
            aq.id(R.id.user_item_first_name).text(news.getUserFirstName());
            aq.id(R.id.user_item_last_name).text(news.getUserLastName());
            aq.id(R.id.user_item_small_avatar).image(news.getUserSmallPicture());
            if(news.getUserParseGeoPoint() != null){
                Double to_me_kilometers = news.getUserParseGeoPoint().distanceInKilometersTo(mypozi);
                aq.id(R.id.user_item_km_to_me).text("Distance " + (String.format("%.2f", to_me_kilometers)) + " km");
            }
        /*    aq.id(R.id.news_text).text(news.getShortText());
            aq.id(R.id.news_category).text(news.getCategory());
            if (news.getViews_of_News() != null) {
                Log.d("views", "ser " + news.getViews_of_News());
            }
            aq.id(R.id.news_photo).image(news.getPhoto_url(), true, true, 0, 0, null, 0, AQuery.RATIO_PRESERVE);
*/

            return v;


        }

        @Override
        public View getNextPageView(View v, ViewGroup parent) {
            if (v == null) {
                v = View.inflate(getContext(), R.layout.showmore, null);
            }
            TextView textView = (TextView) v.findViewById(R.id.show_more_text);
            textView.setText("Loaded " + getCount() + " rows. Get more!");
            return v;
        }


    }
}
