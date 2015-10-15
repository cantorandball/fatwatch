package com.candb.josh.protochi;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CounterFragment extends Fragment {

    private boolean mInitialised;

    public static CounterFragment newInstance() {
        CounterFragment fragment = new CounterFragment();
        return fragment;
    }

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mInitialised = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_counter, container, false);
    }

    public void countTest(String intToShow){
        TextView showMeAgain = (TextView) getActivity().findViewById(R.id.test_message);
        showMeAgain.setText(intToShow);
    }
}
