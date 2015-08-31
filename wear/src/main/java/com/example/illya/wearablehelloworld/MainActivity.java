package com.example.illya.wearablehelloworld;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    // Can add DataApi.DataListener, MessageApi.MessageListener, and NodeApi.NodeListener
    // for listening to events from the phone.

    private TextView mTextView;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
            }
        });
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    public void onDataChanged(DataEventBuffer dataEventBuffer) {

    }

    public void onConnected(Bundle bundle) {

    }

    public void onConnectionSuspended(int i) {

    }

    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    //Would work if we had events to send, otherwise nodeId is undefined.
    /*
    public void sendMessage() {
        Node nodeId;
        NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).await();
        for (Node node: nodes.getNodes()){

            //Do something with node.getId(), like send a message
        }
        Wearable.MessageApi.sendMessage(mGoogleApiClient, nodeId, "/path/message", new byte[0]).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
            public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                if (!sendMessageResult.getStatus().isSuccess()) {
                    //Log.e(TAG, "Failed to send step count data item " + path );
                } else {
                    //Log.d(TAG, "Successfully sent step count data item " + path );
                }
            }
        });
    }
    */

    public void sendStepCount(int steps, long timestamp) {
        PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/step-counter");
        putDataMapRequest.getDataMap().putInt("step-count", steps);
        putDataMapRequest.getDataMap().putLong("timestamp", timestamp);

        PutDataRequest request = putDataMapRequest.asPutDataRequest();
        Wearable.DataApi.putDataItem(mGoogleApiClient, request)
                .setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                    @Override
                    public void onResult(DataApi.DataItemResult dataItemResult) {
                        if (!dataItemResult.getStatus().isSuccess()) {
                            //Log.e(TAG, "Failed to send step count data item " + path );
                        } else {
                            //Log.d(TAG, "Successfully sent step count data item " + path );
                        }
                    }
                });


    }
}
