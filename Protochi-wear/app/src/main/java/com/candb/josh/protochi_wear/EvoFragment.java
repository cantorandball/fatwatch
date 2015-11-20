package com.candb.josh.protochi_wear;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EvoFragment extends GenericFragment {

    private int chiStage = 1;
    private final int chiTotalStages = 6;

    private double movementTotal = 0.0;
    private final int levelThreshold = 1000;
    private int activityLevel = 1;

    private double movementThisLevel = 0.0;
    private double lastKnownMovementValue = 0.0;
    private View mView;

    private String LOG_TAG = "PROTOCHI_EVO_FRAGMENT";

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

    // Given some accel data, this function prompt the correct reaction
    public void evoReact(double accel, double movement){

        // Update last known value, and get change
        double movementDelta = movement - lastKnownMovementValue;
        int thresholdForStage = levelThreshold * chiStage;

        lastKnownMovementValue = movement;
        if (movementDelta > 0){
           movementThisLevel += movementDelta;
        }
        if ( movementThisLevel < thresholdForStage){
            animateChi(accel);
        }else{
            movementThisLevel -= thresholdForStage;
            evolveChi();
        }
    }

    public void updateChiAvatar(){
        ImageView chiAvatar = (ImageView) getActivity().findViewById(R.id.chi_avatar);
        String drawableName = "stage" + Integer.toString(chiStage) + "_beam";
        int imageId = getResources().getIdentifier(drawableName, "drawable", "com.candb.josh.protochi_wear");
        chiAvatar.setImageResource(imageId);
    }

    public void animateChi(double accel){
    }

    public void evolveChi(){
        if ( chiStage < chiTotalStages) {
            chiStage += 1;
        }
        updateChiAvatar();
    }

    @Override
    public void resetValues(){
        super.resetValues();
        lastKnownMovementValue = 0.0;
        chiStage = 1;
        movementThisLevel = 0.0;
        updateChiAvatar();
    }
}
