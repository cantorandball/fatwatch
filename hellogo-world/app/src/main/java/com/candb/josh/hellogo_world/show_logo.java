package com.candb.josh.hellogo_world;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class show_logo extends WearableActivity {

    LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_logo);
    }

}
