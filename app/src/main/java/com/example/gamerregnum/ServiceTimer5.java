package com.example.gamerregnum;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ServiceTimer5 extends Service {

    CountDownTimer Count5;

    public ServiceTimer5() {
    }

    public void onCreate() {
        super.onCreate();
        notifi_StartForeground();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Count5.cancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        SharedPreferences preferences = this.getSharedPreferences("Archivo_DataTimer5", Context.MODE_PRIVATE);
        long TIME_LIMIT = preferences.getLong("Tiempo5",0 );
        Count5 = new CountDownTimer(TIME_LIMIT, 1000) {
            public void onTick(long millisUntilFinished) {
                Intent i = new Intent("BROADCAST_TIMER5");
                i.putExtra("timer5", millisUntilFinished);
                sendBroadcast(i);
            }

            public void onFinish() {
                Intent i = new Intent("BROADCAST_TIMER5");
                i.putExtra("timer5", "00:00:00");
                sendBroadcast(i);
                SharedPreferences preferences5 = getSharedPreferences("Archivo_Name5", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences5.edit();
                editor.putString("Name5", "Add New");
                editor.apply();
                stopForeground(true);
                notificaciones();
                stopSelf();
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void  notifi_StartForeground(){
        SharedPreferences preferences = this.getSharedPreferences("Archivo_Name5", Context.MODE_PRIVATE);
        String valor5 = preferences.getString("Name5","Add New" );
        String CHANNEL_ID = "Hi";
        Intent resultIntent = new Intent(this, PagePrimary.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity( this , 0 , resultIntent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notific)
                .setAutoCancel(true)
                .setContentTitle("Timer 5 active")
                .setContentText("Name: "+ valor5)
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "chanel";
            String description = "chanel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        mNotificationManager.notify(5, builder.build());
        startForeground(5, builder.build());

        //startForeground(1, new Notification(0, null, System.currentTimeMillis()));
    }

    private void  notificaciones(){
        String CHANNEL_ID = "Hi";
        Intent resultIntent = new Intent(this, PagePrimary.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity( this , 0 , resultIntent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notific)
                .setContentTitle("Timer")
                .setContentText("Timer 5 completed" )
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "chanel";
            String description = "chanel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.songtimer);
        mp.start();

        mNotificationManager.notify(5, builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
