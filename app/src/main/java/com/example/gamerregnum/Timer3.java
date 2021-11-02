package com.example.gamerregnum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;

import java.util.concurrent.TimeUnit;

public class Timer3 extends AppCompatActivity {

    NumberPicker h3;
    NumberPicker m3;
    NumberPicker s3;
    public int hourPicked3;
    public int minPicked3;
    public int segPicked3;
    public Button BTNSig3;
    public Button BTNCancel3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer3);

        BTNSig3 = findViewById(R.id.btnSig3);
        BTNCancel3 = findViewById(R.id.btnCancel3);
        h3 = findViewById(R.id.numberPickerHora3);
        m3 = findViewById(R.id.numberPickerMinuto3);
        s3 = findViewById(R.id.numberPickerSegundo3);
        h3.setMinValue(0);
        h3.setMaxValue(99);
        m3.setMinValue(0);
        m3.setMaxValue(59);
        s3.setMinValue(0);
        s3.setMaxValue(59);

        BTNSig3.setEnabled(false);

        SharedPreferences preferences = this.getSharedPreferences("Archivo_Name3", Context.MODE_PRIVATE);
        String valor1 = preferences.getString("Name3","Add New" );
        if (valor1.equals("Add New")){
            BTNCancel3.setVisibility(View.INVISIBLE);
        }

        h3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hourPicked3 = newVal;
                if (newVal != 0){
                    BTNSig3.setEnabled(true);
                }
            }
        });

        m3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minPicked3 = newVal;
                if (newVal != 0){
                    BTNSig3.setEnabled(true);
                }
            }
        });

        s3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                segPicked3 = newVal;
                if (newVal != 0){
                    BTNSig3.setEnabled(true);
                }
            }
        });

        registerReceiver(MyBroadcast3, new IntentFilter("BROADCAST_TIMER3"));

    }

    public void btnCancel3(View view) {
        stopService(new Intent(Timer3.this, ServiceTimer3.class));
        long TIME_LIMIT = 0;
        SharedPreferences preferences = getSharedPreferences("Archivo_DataTimer3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("Tiempo3", TIME_LIMIT);
        editor.apply();
        SharedPreferences preferences2 = getSharedPreferences("Archivo_Name3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.putString("Name3", "Add New");
        editor2.apply();
        h3.setEnabled(true);
        m3.setEnabled(true);
        s3.setEnabled(true);
        h3.setValue(0);
        m3.setValue(0);
        s3.setValue(0);
    }

    private BroadcastReceiver MyBroadcast3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer3")) {
                    long millis = (intent.getLongExtra("timer3", 0));
                    long hrs = TimeUnit.MILLISECONDS.toHours(millis) % 99;
                    long min = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
                    long sec = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
                    h3.setEnabled(false);
                    m3.setEnabled(false);
                    s3.setEnabled(false);
                    h3.setValue((int) hrs);
                    m3.setValue((int) min);
                    s3.setValue((int) sec);
                }
            }
        }
    };

    public void btnSig3 (View view) {
        Intent i = new Intent(Timer3.this, MyApps3.class);
        long TIME_LIMIT = ((hourPicked3 * 1000 * 60 * 60) + (minPicked3 * 60 * 1000) + (segPicked3 * 1000));
        SharedPreferences preferences = getSharedPreferences("Archivo_DataTimer3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("Tiempo3", TIME_LIMIT);
        editor.apply();
        startActivity(i);
    }

}