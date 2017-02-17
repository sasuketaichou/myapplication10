package com.example.mierul.myapplication10;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class NotificationAlarmService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flag, int startId)
    {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        /** This is where u should put what to do after click notification, its on the pending intent**/
        Intent mIntent = new Intent(this, MainActivity.class);

        /* ATTENTION : Pending intent  = nullpointer exception? i dont know y */
        PendingIntent pendingIntent = PendingIntent.getActivity(this, intent.getIntExtra("notifId", 0), mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Reminder");
        builder.setContentText("Its Your Birthday!!");
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);

        notificationManager.notify(intent.getIntExtra("notifId", 0), builder.build());

        return super.onStartCommand(intent, flag, startId);
    }
}
