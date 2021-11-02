package com.example.gamerregnum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

public class Splash_Screen extends AppCompatActivity {

    VideoView Video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        Video = findViewById(R.id.videoView);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash);
        Video.setVideoURI(video);
        Video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                startNextActivity();
            }
        });
        Video.start();
    }

    private void startNextActivity() {
        SharedPreferences preferences10 = this.getSharedPreferences("ID", Context.MODE_PRIVATE);
        String UID = preferences10.getString("ID2","0" );
        if (isFinishing()){
            return;
        }
        if (UID.equals("0")){
            startActivity(new Intent(this, MainActivity.class));
        }
        else {
            startActivity(new Intent(this, PagePrimary.class));
        }
        finish();
    }


}