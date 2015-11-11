package com.candb.josh.protochi_wear;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CounterFragment extends GenericFragment {

    public View mView;
    public double currentMaxAccel = 0;

    public static CounterFragment newInstance() {
        return new CounterFragment();
    }

    public CounterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_counter, container, false);
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

    public double twoDecimalPlaces(Double inDouble){
        return Double.parseDouble(new DecimalFormat("#.##").format(inDouble));
    }

    public void getHighestAccel(double newAccel){
        currentMaxAccel = Math.max(newAccel, currentMaxAccel);
    }

    public void displayValues(double accel, double sum){
        getHighestAccel(accel);

        String strSum = String.valueOf(twoDecimalPlaces(sum));
        String strAccel = String.valueOf(twoDecimalPlaces(accel));
        String strMaxAccel = String.valueOf(twoDecimalPlaces(currentMaxAccel));

        TextView sumView = (TextView) getActivity().findViewById(R.id.counter_total);
        TextView accelView = (TextView) getActivity().findViewById(R.id.counter_accel);
        TextView maxAccelView = (TextView) getActivity().findViewById(R.id.counter_max_accel);

        sumView.setText(strSum);
        accelView.setText(strAccel);
        maxAccelView.setText(strMaxAccel);
    }
}
