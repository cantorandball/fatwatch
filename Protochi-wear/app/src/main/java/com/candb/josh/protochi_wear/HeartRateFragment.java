package com.candb.josh.protochi_wear;


import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class HeartRateFragment extends GenericFragment {

    public View mView;
    private int heartReadings = 0;

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

    // Set up context menu
    public void onResume(){
        super.onResume();
        registerForContextMenu(mView);
    }

    @Override
    public void onPause()
    {
        unregisterForContextMenu(mView);
        super.onPause();
    }

    public void updateCurrentRate(float rate){
        heartReadings += 1;

        String strRate = String.valueOf(rate);
        String strReadings = String.valueOf(heartReadings);

        TextView readingsView = (TextView) getActivity().findViewById(R.id.no_of_heart_readings);
        TextView rateView = (TextView) getActivity().findViewById(R.id.heart_rate_now);
        rateView.setText(strRate);

        Log.i("Heartthing:", "Setting texts: " + strRate + ' ' + strReadings);
        readingsView.setText(strReadings);
    }
}
