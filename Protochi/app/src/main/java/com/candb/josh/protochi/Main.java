/* Display accelerometer movement.
 *
 * Most of this code by Wiliam J. Francis
 *
 * Retrieved 01/10/2015 from
 * www.techrepublic.com/blog/software-engineer/a-quick-tutorial-on-coding-androids-accelerometer
 * 
 * */

package com.candb.josh.protochi;

import android.content.Context;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.Window;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class Main extends FragmentActivity implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private final float NOISE = (float) 2.0;
    private int testCounter = 0;
    private ViewPager mainPager;
    public String LOG_TAG = "PROTOCHI_MESSAGING_SERVICE";


    // Called when activity first initialised
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // Start up a ViewPager, allowing us to view fragments on different pages.
        mainPager = (ViewPager) findViewById(R.id.main_view_pager);
        mainPager.setAdapter(new WatchPagerAdapter(getSupportFragmentManager()));

        // Set up sensor event stuff
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

 /* TODO: Work out how to use these lifecycle events

    @Override

    public void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }*/

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
                    return IndicatorFragment.newInstance();
                case 1:
                    return CounterFragment.newInstance();
                default:
                    return IndicatorFragment.newInstance();
            }
        }

        @Override
        public int getCount(){
            return 2;
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

    // Required sensor methods
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        // Check for available fragments

    }

    public void onSensorChanged(SensorEvent event){
        // TODO: Remove this test bit
        testCounter += 1;

        // Get current fragment from the ViewPager adapter
        WatchPagerAdapter mainAdapter = (WatchPagerAdapter) mainPager.getAdapter();
        Fragment currentFragment = mainAdapter.getRegisteredFragment(mainPager.getCurrentItem());

        if (currentFragment != null) {
            if (currentFragment instanceof IndicatorFragment){
                Log.d(LOG_TAG, "Firing indication fragment");
                IndicatorFragment indicatorFragment = (IndicatorFragment) currentFragment;
                indicatorFragment.updateSensorIndicator(event, NOISE);
            } else if (currentFragment instanceof CounterFragment){
                Log.d(LOG_TAG, "Firing counter fragment");
                CounterFragment counterFragment = (CounterFragment) currentFragment;
                counterFragment.countTest(String.valueOf(testCounter));
            } else {
                Log.e(LOG_TAG, "Fragment of unknown instance type passed to adapter");
            }
        } else {
            Log.w(LOG_TAG, "No fragments exist");
        }
    }
}





