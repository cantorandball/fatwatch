package com.candb.josh.protochi_wear;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EvoFragment extends GenericFragment {

    private int chiStage = 1;
    private final int chiTotalStages = 6;

    private final int levelThreshold = 1000;

    private double movementThisLevel = 0.0;
    private double lastKnownMovementValue = 0.0;
    private View mView;

    private String LOG_TAG = "PROTOCHI_EVO_FRAGMENT";

    int eventsToAverage = 20;
    ArrayList<Double> valuesArray = new ArrayList<Double>(eventsToAverage);

    // Implement progress bar
    private ProgressBar mProgressBar;
    private double mProgressStatus = 0.0;

    public EvoFragment() {
        // Required empty public constructor
    }

    public static EvoFragment newInstance() {
        return new EvoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_evo, container, false);
        mProgressBar = (ProgressBar) mView.findViewById(R.id.evo_progress);

        return mView;
    }

    // Set up context menu
    public void onResume(){
        super.onResume();
        registerForContextMenu(mView);
    }

    @Override
    public void onPause(){
        unregisterForContextMenu(mView);
        super.onPause();
    }

    // Given an acceleration, return an 'emotion' string, used to
    // animate the chi
    public String getExpression(double accel){
        String expression = "smirk";
        if (accel <= 0){
            expression = "glum";
        } else if (accel > 0 && accel <= 10) {
            expression = "smirk";
        } else if (accel > 10 && accel <= 20) {
            expression = "smile";
        } else {
            expression = "beam";
        }
        return expression;
    }

    // Given some accel data, this function prompts the correct reaction
    public void evoReact(double accel, double movement){

        // Update last known value, and get change
        double movementDelta = movement - lastKnownMovementValue;
        int thresholdForStage = levelThreshold * chiStage;

        // If movement has taken place, update the value
        lastKnownMovementValue = movement;
        if (movementDelta > 0){
           movementThisLevel += movementDelta;
        }

        // Either animate or evolve the Chi, depending on how far you've moved.
        if (movementThisLevel < thresholdForStage){
            animateChi(accel);
            // Update progress bar
            double progress = (movementThisLevel / thresholdForStage) * 100;
            int progressInt = (int) progress;
            Log.i(LOG_TAG, "Setting progress to " + Integer.toString(progressInt));
            mProgressBar.setProgress(progressInt);

        }else{
            movementThisLevel -= thresholdForStage;
            evolveChi();
        }

    }

    public void updateChiAvatar(int stage, String expression){
        ImageView chiAvatar = (ImageView) getActivity().findViewById(R.id.chi_avatar);
        String drawableName = "stage" + Integer.toString(stage) + "_" + expression;
        int imageId = getResources().getIdentifier(drawableName, "drawable", "com.candb.josh.protochi_wear");
        chiAvatar.setImageResource(imageId);
    }

    public void animateChi(double accel){
        double averageValue = averageLastNValues(accel, eventsToAverage, valuesArray);
        String expression = getExpression(averageValue);
        updateChiAvatar(chiStage, expression);
    }

    public void evolveChi(){
        if ( chiStage < chiTotalStages) {
            chiStage += 1;
        }
        updateChiAvatar(chiStage, "smirk");
        mProgressBar.setProgress(0);
    }

    @Override
    public void resetValues(){
        super.resetValues();
        lastKnownMovementValue = 0.0;
        chiStage = 1;
        movementThisLevel = 0.0;
        updateChiAvatar(chiStage, "smirk");
    }
}
