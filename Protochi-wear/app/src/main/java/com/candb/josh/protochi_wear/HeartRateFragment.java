package com.candb.josh.protochi_wear;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

public class HeartRateFragment extends GenericFragment {

    public View mView;

    // Set up heart rate refresh button animation
    RotateAnimation spinRefresh = new RotateAnimation(0,360,12,12);

    public HeartRateFragment() {
        // Required empty public constructor
    }

    public static HeartRateFragment newInstance() {
        return new HeartRateFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Callback for reading heart rate

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

    public void displayCurrentRate(String rate){

        TextView rateView = (TextView) getActivity().findViewById(R.id.heart_rate_now);
        ImageButton refreshArrows = (ImageButton) getActivity().findViewById(R.id.heart_rate_reread);

        rateView.setText(rate);

        if (rate == getText(R.string.heart_rate_in_progress)){
            if ( !spinRefresh.hasStarted() ){
                Log.i("Willies: ", "Starting animation");
                refreshArrows.startAnimation(spinRefresh);
            }

        } else {
            if ( spinRefresh.hasStarted() ){
                Log.i("Willies: ", "Stopping animation");
                refreshArrows.setAnimation(null);
            }
        }
    }
}
