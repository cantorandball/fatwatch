package com.candb.josh.protochi_wear;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Date;

public class HeartRateFragment extends GenericFragment {

    private String LOG_TAG = "PROTOCHI_MESSAGING_SERVICE: HEART";
    public View mView;

    private Date lastHeartReadingTime = new Date(0);
    private float lastHeartReadingValue = 0;

    private Date timeNow = new Date();
    private int heartRateTimeout = 120000;

    //Callback for reading heart rate
    private FragmentCallback activityCallback;

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


        ImageButton refreshButton = (ImageButton) mView.findViewById(R.id.heart_rate_reread);

        refreshButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                activityCallback.readHeartRate();
            }
        });

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



    // For refresh button
    public void updateCurrentRate(float rate){

        // If you've read a new value, update the display
        if (rate != lastHeartReadingValue && rate > 0.0){
            lastHeartReadingTime = new Date();
        }

        TextView rateView = (TextView) getActivity().findViewById(R.id.heart_rate_now);

        // Work out seconds since last reading
        timeNow = new Date();
        long timeSinceLastReading = timeNow.getTime() - lastHeartReadingTime.getTime();

        Log.w(LOG_TAG, "seconds: " + Long.toString(timeSinceLastReading));

        if (timeSinceLastReading >= heartRateTimeout){
            rateView.setText(R.string.heart_rate_init_value);
        } else {
            String strRate = String.valueOf(rate);
            rateView.setText(strRate);
        }
        lastHeartReadingValue = rate;
    }
}
