package com.candb.josh.protochiphone;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity{

    //How long to wait to connect to smartwatch
    public int CONNECTION_TIME_OUT_MS = 10000;
    public String NODE_ID;
    private String LOG_TAG = "PhoneMessaging Debugger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        retrieveDeviceNode();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private GoogleApiClient getGoogleApiClient(Context context){
        return new GoogleApiClient.Builder(context)
                .addApi(Wearable.API)
                .build();
    }

    // Find out what's connected to the phone
    private void retrieveDeviceNode(){

        Log.i(LOG_TAG, "Getting device node");
        final GoogleApiClient client = getGoogleApiClient(this);

        // Start a background thread
        new Thread(new Runnable() {
            @Override
            public void run(){
                Log.i(LOG_TAG, "Running thread to get ID");
                client.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                NodeApi.GetConnectedNodesResult result =
                        Wearable.NodeApi.getConnectedNodes(client).await();
                List<Node> nodes = result.getNodes();
                if (nodes.size() > 0) {
                    NODE_ID =  nodes.get(0).getId();
                }else{
                    Log.e(LOG_TAG, "No nodes found");
                }
                client.disconnect();
                sendToast();
            }
        }).start();
    }

    private void sendToast() {
        Log.i(LOG_TAG, "Sending toast");
        Log.i(LOG_TAG, "Node tag: " + NODE_ID);
        final GoogleApiClient client = getGoogleApiClient(this);
        if (NODE_ID != null){
            new Thread(new Runnable(){
                @Override
                public void run(){
                    client.blockingConnect(CONNECTION_TIME_OUT_MS, TimeUnit.MILLISECONDS);
                    Wearable.MessageApi.sendMessage(client, NODE_ID, "FANG TINGS BRUH", null);
                    client.disconnect();
                }
            }).start();
        }else{
            Log.e(LOG_TAG, "No devices connected");
        }
    }
}
