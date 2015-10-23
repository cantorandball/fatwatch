package com.candb.josh.protochi;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ListIterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccelColourFragment extends Fragment {

    int eventsToAverage = 4;
    ArrayList<Double> valuesArray = new ArrayList<Double>(eventsToAverage);

    public AccelColourFragment() {
        // Required empty public constructor
    }

    public static AccelColourFragment newInstance() {
        return new AccelColourFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accel_colour, container, false);
    }

    private double averageLastNValues(double value, int n){
        double sum = 0.0;
        valuesArray.add(0,value); //Add value to the beginning of the array, incrementing all others
        int currentSize = valuesArray.size();
        if (currentSize == n){
            valuesArray.remove(n-1);
        }
        for (Double v : valuesArray){
            sum += v;
        }
        return sum / currentSize;
    }

    private int getColour(double value){
        float[] HSBArray = new float[3];
        double hue = value * 100; // 0.4 is the value for green
        double saturation = 0.9;
        double brightness = 0.9;

        HSBArray[0] = (float) hue;
        HSBArray[1] = (float) saturation;
        HSBArray[2] = (float) brightness;

        return Color.HSVToColor(HSBArray);
    }

    public void setBackgroundColour(double accel){
        double maxAccel = 25.0;
        double smoothedAccel = averageLastNValues(accel, eventsToAverage);
        double adjustedAccel = 1 - (Math.min(Math.abs(smoothedAccel), maxAccel)/maxAccel);
        int colourToSet = getColour(adjustedAccel);

        TextView accelFrame = (TextView) getActivity().findViewById(R.id.colour_square);
        accelFrame.setBackgroundColor(colourToSet);
    }
}
