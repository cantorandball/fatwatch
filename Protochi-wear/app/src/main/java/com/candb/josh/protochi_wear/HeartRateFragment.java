package com.candb.josh.protochi_wear;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HeartRateFragment extends GenericFragment {

    public View mView;

    public static HeartRateFragment newInstance() {
        return new HeartRateFragment();
    }

    public HeartRateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_heart_rate, container, false);
        return mView;
    }
}
