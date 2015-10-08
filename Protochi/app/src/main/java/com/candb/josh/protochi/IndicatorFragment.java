package com.candb.josh.protochi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class IndicatorFragment extends Fragment {

    public static IndicatorFragment newInstance() {
        IndicatorFragment fragment = new IndicatorFragment();
        return fragment;
    }

    public IndicatorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_indicator, container, false);
    }
}
