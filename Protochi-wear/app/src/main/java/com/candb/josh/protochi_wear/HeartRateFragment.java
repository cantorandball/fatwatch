package com.candb.josh.protochi_wear;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Date;

public class HeartRateFragment extends GenericFragment {

    private String LOG_TAG = "PROTOCHI_MESSAGING_SERVICE: HEART";
    public View mView;

    private Date lastHeartReading = new Date(0);
    private Date timeNow = new Date();
    private int heartRateTimeout = 60;

    public HeartRateFragment() {
        // Required empty public constructor
    }

    public static HeartRateFragment newInstance() {
        return new HeartRateFragment();
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

        TextView rateView = (TextView) getActivity().findViewById(R.id.heart_rate_now);

        // Work out seconds since last reading
        timeNow = new Date();
        long secondsSinceLastReading = (timeNow.getTime() - lastHeartReading.getTime())/1000;

        Log.w(LOG_TAG, "seconds: " + Long.toString(secondsSinceLastReading));

        if (secondsSinceLastReading >= heartRateTimeout){
            rateView.setText("Not yet taken");
        } else {
            String strRate = String.valueOf(rate);
            rateView.setText(strRate);
        }
    }
}
