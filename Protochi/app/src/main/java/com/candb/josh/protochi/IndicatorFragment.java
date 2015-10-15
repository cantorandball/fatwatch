package com.candb.josh.protochi;

import android.hardware.SensorEvent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class IndicatorFragment extends Fragment{

    OnSensorChangeListener mCallback;

    public interface OnSensorChangeListener{
    }

    public void onAttach(Main activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnSensorChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    private float mLastX, mLastY, mLastZ;
    private boolean mInitialized;

    public static IndicatorFragment newInstance() {
        IndicatorFragment fragment = new IndicatorFragment();
        return fragment;
    }

    public IndicatorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mInitialized = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_indicator, container, false);
        return rootView;
    }

    public void counterTest(String intToShow){
        TextView showMe = (TextView) getActivity().findViewById(R.id.xaxis);
        showMe.setText(intToShow);
    }

    public void updateSensorIndicator(SensorEvent event, Float noise) {
        TextView tvX = (TextView) getActivity().findViewById(R.id.xaxis);
        TextView tvY = (TextView) getActivity().findViewById(R.id.yaxis);
        TextView tvZ = (TextView) getActivity().findViewById(R.id.zaxis);
        ImageView iv = (ImageView) getActivity().findViewById(R.id.image);

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float max = 0;
        Collection<Float> deltas = new ArrayList<Float>();

        // Initialise values for first call
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;

            tvX.setText("0.0");
            tvY.setText("0.0");
            tvZ.setText("0.0");

            mInitialized = true;
        } else {
            // Calculate change in values
            float deltaX = Math.abs(mLastX - x);
            float deltaY = Math.abs(mLastY - y);
            float deltaZ = Math.abs(mLastZ - z);

            // If less than NOISE, set to 0
            if (deltaX < noise) deltaX = (float) 0.0;
            if (deltaY < noise) deltaY = (float) 0.0;
            if (deltaZ < noise) deltaZ = (float) 0.0;

            // Save current values as 'mLast'
            mLastX = x;
            mLastY = y;
            mLastZ = z;

            // Update text views
            tvX.setText(Float.toString(deltaX));
            tvY.setText(Float.toString(deltaY));
            tvZ.setText(Float.toString(deltaZ));

            // Calculate dominant value
            deltas.clear();
            deltas.add(deltaX);
            deltas.add(deltaY);
            deltas.add(deltaZ);
            max = Collections.max(deltas);

            // Set image to correct icon
            iv.setVisibility(View.VISIBLE);

            if (max == 0.0) {
                iv.setVisibility(View.INVISIBLE);
            } else if (max == deltaX) {
                iv.setImageResource(R.drawable.xaxis);
            } else if (max == deltaY) {
                iv.setImageResource(R.drawable.yaxis);
            } else if (max == deltaZ) {
                iv.setImageResource(R.drawable.zaxis);
            } else {
                iv.setVisibility(View.INVISIBLE);
            }
        }
    }




}
