package com.example.gamerregnum;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class ServiceTimer extends Service {

    CountDownTimer Count;
    String timer1;

    public ServiceTimer() {
    }

    public void onCreate() {
        super.onCreate();
        registerReceiver(MyBroadcast, new IntentFilter("BROADCAST_TIMER1"));
        notifi_StartForeground();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Count.cancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        SharedPreferences preferences = this.getSharedPreferences("Archivo_DataTimer", Context.MODE_PRIVATE);
        long TIME_LIMIT = preferences.getLong("Tiempo1",0 );

        Count = new CountDownTimer(TIME_LIMIT, 1000) {
            public void onTick(long millisUntilFinished) {
                Intent i = new Intent("BROADCAST_TIMER1");
                i.putExtra("timer1", millisUntilFinished);
                sendBroadcast(i);
            }
            public void onFinish() {
                Intent i = new Intent("BROADCAST_TIMER1");
                i.putExtra("timer1", "00:00:00");
                sendBroadcast(i);
                SharedPreferences preferences = getSharedPreferences("Archivo_Name", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Name1", "Add New");
                editor.apply();
                stopForeground(true);
                notificaciones();
                stopSelf();
            }
        }.start();
        return START_STICKY;
    }

    private void  notifi_StartForeground(){
        SharedPreferences preferences = this.getSharedPreferences("Archivo_Name", Context.MODE_PRIVATE);
        String valor1 = preferences.getString("Name1","Add New" );
        String CHANNEL_ID = "Hi";
        Intent resultIntent = new Intent(this, PagePrimary.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity( this , 0 , resultIntent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notific)
                .setWhen(System.currentTimeMillis())
                .setShowWhen(true)
                .setContentTitle("Timer 1 active")
                .setContentText("Name: "+ valor1)
                //.setContentText("Timer: "+ timer1 + " - "+ valor1)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.VISIBILITY_PUBLIC);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "chanel";
            String description = "chanel_description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        mNotificationManager.notify(1, builder.build());
        startForeground(1, builder.build());

        //startForeground(1, new Notification(0, null, System.currentTimeMillis()),);

    }

    private void  notificaciones(){

        String CHANNEL_ID = "Hi";
        Intent resultIntent = new Intent(this, PagePrimary.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent resultPendingIntent = PendingIntent.getActivity( this , 0 , resultIntent, 0);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notific)
                .setAutoCancel(true)
                .setContentTitle("Timer")
                .setContentText("Timer 1 completed")
                .setDefaults(Notification.DEFAULT_LIGHTS)
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

        /*AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(ALARM_SERVICE);
        Intent alarmIntent = new Intent(ctx, AlarmReceiver.class);
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(ctx, i, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        alarmIntent.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);*/

        mNotificationManager.notify(1, builder.build());
    }

    private BroadcastReceiver MyBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer1")) {
                    long millis = (intent.getLongExtra("timer1", 0));
                    timer1 = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                }
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
