package com.candb.josh.protochi;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccelColourFragment extends Fragment {


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

    public void setBackgroundColour(){
        TextView accelFrame = (TextView) getActivity().findViewById(R.id.colour_square);
        accelFrame.setBackgroundColor(Color.GREEN);
    }
}
