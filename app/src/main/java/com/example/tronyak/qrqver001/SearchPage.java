package com.example.tronyak.qrqver001;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.ParseException;

/**
 * Created by Yura on 04.09.2015.
 */
public class SearchPage extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        String qr_id = bundle.getString("qr_id");
        Searcher_query(qr_id, 0);
        Snackbar.make(container,qr_id, Snackbar.LENGTH_LONG).show();
        return inflater.inflate(R.layout.search, container, false);
    }
    public void Searcher_query(final String s, final int i){

        final String[] m_tables = {"news","Quest","geopoints","User"};

        ParseQuery<ParseObject> query = ParseQuery.getQuery(m_tables[i].toString());
        query.whereEqualTo("objectId" , s);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, com.parse.ParseException e) {
                if (parseObject == null) {
                    Log.d("search", "The " + m_tables[i] + " request failed.");
                    if (i <  m_tables.length-1) {
                        Searcher_query(null, i+1);
                    }else {
                        Snackbar.make(getView(),"We can not find enything", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("score", "we found this");
                    Snackbar.make(getView(),"We found this in " + m_tables[i], Snackbar.LENGTH_LONG).show();

                }
            }
        });
    }

}
