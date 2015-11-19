package com.candb.josh.protochi_wear;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

import java.util.concurrent.TimeUnit;

/**
 * Created by josh on 11/11/2015.
 */
public class ListenerService extends WearableListenerService{

    String nodeId;
    int CONNECTION_TIME_OUT_MS = 1000;

    @Override
    public void onMessageReceived(MessageEvent messageEvent){
        nodeId = messageEvent.getSourceNodeId();
        Log.i("ProtochiListener", "Got message");
        showToast(messageEvent.getPath());
    //    reply("Dunnit");
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /*private void reply(String message) {
        GoogleApiClient client = new GoogleApiClient.Builder(this.getApplicationContext())
                .addApi(Wearable.API)
                .build();
        client.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
        Wearable.MessageApi.sendMessage(client, nodeId, message, null);
        client.disconnect();
    }*/
}
