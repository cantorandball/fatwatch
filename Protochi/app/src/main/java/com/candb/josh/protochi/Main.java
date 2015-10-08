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
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class Main extends FragmentActivity{
    // Called when activity first initialised
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // Start up a ViewPager, allowing us to view fragments on different pages.
        ViewPager mainPager = (ViewPager) findViewById(R.id.main_view_pager);
        mainPager.setAdapter(new WatchPagerAdapter(getSupportFragmentManager()));

    }

    private class WatchPagerAdapter extends FragmentPagerAdapter{

        public WatchPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){

                case 0: return IndicatorFragment.newInstance();
                case 1: return CounterFragment.newInstance();
                default: return IndicatorFragment.newInstance();
            }
        }

        @Override
        public int getCount(){
            return 2;
        }
    }
}





