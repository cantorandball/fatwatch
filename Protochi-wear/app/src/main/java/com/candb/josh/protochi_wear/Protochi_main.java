package com.candb.josh.protochi_wear;

import android.content.ComponentName;
import android.content.Context;


import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.ViewGroup;
import android.view.Window;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import android.util.SparseArray;
import android.view.WindowManager;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Arrays;

public class Protochi_main extends FragmentActivity implements SensorEventListener {

    // Set up sensors
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mHeartRateSensor;

    // Set up Google Fit implementation
    private GoogleFitConnector mGoogleFitConnector;

    private final float NOISE = (float) 20.0;
    private ViewPager mainPager;
    public String LOG_TAG = "PROTOCHI_MESSAGING_SERVICE";

    // For counting
    private float mLastX;
    private float mLastY;
    private float mLastZ;
    private boolean mInitialised = false;
    private double movement = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_protochi_main);

        //Watch specific code
        //setAmbientEnabled();

        // Keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        /* Start Google Play API activity
        Intent googleFitIntent = new Intent(this, GoogleFitConnector.class);
        startActivity(googleFitIntent);*/

        // Start up a ViewPager, allowing us to view fragments on different pages.
        mainPager = (ViewPager) findViewById(R.id.main_view_pager);
        mainPager.setAdapter(new WatchPagerAdapter(getSupportFragmentManager()));

        // Set up sensor event stuff
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mHeartRateSensor = mSensorManager.getDefaultSensor(65538);
    }



    //Sensor lifecyle management
    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Start message listening service
        Intent listenerIntent = new Intent(this, ListenerService.class);
        startService(listenerIntent);
    }


    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    /* Watch specific code. Need to find somewhere to put this
    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    } */

    private class WatchPagerAdapter extends FragmentStatePagerAdapter {

        // Register fragments
        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public WatchPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    return HeartRateFragment.newInstance();
                case 1:
                    return CounterFragment.newInstance();
                case 2:
                    return AccelColourFragment.newInstance();
                case 3:
                    return IndicatorFragment.newInstance();
                default:
                    return HeartRateFragment.newInstance();
            }
        }

        @Override
        public int getCount(){
            return 4;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position){
            //When instantiateItem is called, add a fragment to our registeredFragments array
            Fragment newFragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, newFragment);
            return newFragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        // Custom method for getting a fragment from the array we're maintaining.
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

    }

    private double calculateAcceleration(SensorEvent event){
        float currentX = event.values[0];
        float currentY = event.values[1];
        float currentZ = event.values[2];

        //Initialise if not done already
        if (!mInitialised){
            mLastX = currentX;
            mLastY = currentY;
            mLastZ = currentZ;

            mInitialised = true;
        }

        // TODO: Change IndicatorFragment to receive these values, so we're not
        // calculating them twice
        float deltaX = Math.abs(mLastX - currentX);
        float deltaY = Math.abs(mLastY - currentY);
        float deltaZ = Math.abs(mLastZ - currentZ);

        if (deltaX < NOISE) deltaX = (float) 0.0;
        if (deltaY < NOISE) deltaX = (float) 0.0;
        if (deltaZ < NOISE) deltaZ = (float) 0.0;

        mLastX = currentX;
        mLastY = currentY;
        mLastZ = currentZ;

        double netAcceleration = Math.sqrt((deltaX * deltaX) +
                (deltaY * deltaY) +
                (deltaZ * deltaZ));
        return netAcceleration;

    }

    // Required sensor methods
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        // Check for available fragments

    }

    public void onSensorChanged(SensorEvent event) {
        Sensor source = event.sensor;

        // Get current fragment from the ViewPager adapter
        WatchPagerAdapter mainAdapter = (WatchPagerAdapter) mainPager.getAdapter();
        Fragment currentFragment = mainAdapter.getRegisteredFragment(mainPager.getCurrentItem());

        if (source.equals(mAccelerometer)){
            accelSensorHandler(event, currentFragment);
        }else if (source.equals(mHeartRateSensor)){
            Log.i(LOG_TAG, "Heart rate received!");

            heartRateSensorHandler(event, currentFragment);
        }else{
            Log.e(LOG_TAG, "Whoa. No Idea what sensor that was.");
        }
    }

    public void accelSensorHandler(SensorEvent event, Fragment currentFragment) {
        double accel = calculateAcceleration(event);
        double accelThreshold = 0.5;
        if (accel < accelThreshold){
            accel = 0.0;
        }
        movement += accel;

        if (currentFragment != null) {
            if (currentFragment instanceof IndicatorFragment) {
                IndicatorFragment indicatorFragment = (IndicatorFragment) currentFragment;
                indicatorFragment.updateSensorIndicator(event, NOISE);
            } else if (currentFragment instanceof CounterFragment) {
                CounterFragment counterFragment = (CounterFragment) currentFragment;
                counterFragment.displayValues(accel, movement);
            } else if (currentFragment instanceof AccelColourFragment) {
                AccelColourFragment accelColourFragment = (AccelColourFragment) currentFragment;
                accelColourFragment.setBackgroundColour(accel);
            }
        } else {
            Log.w(LOG_TAG, "No fragments exist");
        }
    }

    public void heartRateSensorHandler(SensorEvent event, Fragment currentFragment) {
        float rate = event.values[2];
        Log.i(LOG_TAG, Arrays.toString(event.values));
        if (currentFragment != null) {
            if (currentFragment instanceof HeartRateFragment){
                HeartRateFragment heartRateFragment = (HeartRateFragment) currentFragment;
                heartRateFragment.updateCurrentRate(rate);
            }
        }

    }

    /*
    private void updateDisplay() {
        if (isAmbient()) {
            // Nothing
        }
    }*/
}
