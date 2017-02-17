package com.example.mierul.myapplication10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationPublisher extends BroadcastReceiver {

    final String TAG = "NotificationPublisher";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context,NotificationAlarmService.class);
        service.putExtra("notifId",intent.getIntExtra("notifId",0));
        context.startService(service);

    }
}

