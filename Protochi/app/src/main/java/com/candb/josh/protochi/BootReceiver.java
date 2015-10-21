package com.candb.josh.protochi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by josh on 21/10/15.
 * From a guide by Andreas Schrade
 */
public class BootReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent){
        Intent mainIntent = new Intent(context, Main.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);
    }
}
