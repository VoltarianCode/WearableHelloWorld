package com.example.illya.wearablehelloworld;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MyService extends WearableListenerService {


    public void onDataChanged (DataEventBuffer dataEvents){
        for (DataEvent dataEvent: dataEvents){
            if(dataEvent.getType() ==DataEvent.TYPE_CHANGED){
                DataMap dataMap = DataMapItem.fromDataItem(dataEvent.getDataItem()).getDataMap();
                String path = dataEvent.getDataItem().getUri().getPath();
                if (path.equals("/step-counter")){
                    int steps = dataMap.getInt("step-count");
                    long time = dataMap.getLong("timestamp");
                }
            }
        }
    }


    public void onMessageReceived(MessageEvent messageEvent){
        if(messageEvent.getPath().equals("/path/message")){
            //Here you can do something with byte array from messageEvent.getData();
            // but we don't have a message event
        }
    }





}
