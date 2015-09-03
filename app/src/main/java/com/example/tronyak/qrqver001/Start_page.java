package com.example.tronyak.qrqver001;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.List;

/**
 * Created by Yura on 01.07.2015.
 */
public class Start_page extends ListFragment {


    private CustomParseQueryAdapter mAdapter;
    public static final String ARG_PAGE = "ARG_PAGE";


    public static Start_page create(int page, String type) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        args.putString("type", "all");
        Start_page fragment = new Start_page();
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

        mAdapter.addOnQueryLoadListener(new ParseQueryAdapter.OnQueryLoadListener<News_elements>() {
            public void onLoading() {
                Log.d("tronyakz","loading");
            }

            @Override
            public void onLoaded(List<News_elements> list, Exception e) {
                Log.d("tronyakz","loaded");
            }

        });

        return rootView;

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public class CustomParseQueryAdapter extends ParseQueryAdapter<News_elements> {
        public CustomParseQueryAdapter(Context context) {
            super(context, new QueryFactory<News_elements>() {
                public ParseQuery create() {
                    ParseQuery query = new ParseQuery("news");
                    query.addDescendingOrder("createdAt");
                    query.setLimit(5);
                    return query;
                }
            });
        }


        @Override
        public View getItemView(final News_elements news, View v, ViewGroup parent) {
            if (v == null) {
                v = View.inflate(getContext(), R.layout.news_element, null);
            }

            super.getItemView(news, v, parent);
            AQuery aq = new AQuery(v);
            aq.id(R.id.news_title).text(news.getTitle());
            aq.id(R.id.news_text).text(news.getShortText());
            aq.id(R.id.news_category).text(news.getCategory());
            if (news.getViews_of_News() != null) {
                Log.d("views", "ser " + news.getViews_of_News());
            }
            aq.id(R.id.news_photo).image(news.getPhoto_url(), true, true, 0, 0, null, 0, AQuery.RATIO_PRESERVE);


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
