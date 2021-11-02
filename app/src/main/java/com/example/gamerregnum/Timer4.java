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

public class Timer4 extends AppCompatActivity {

    NumberPicker h4;
    NumberPicker m4;
    NumberPicker s4;
    public int hourPicked4;
    public int minPicked4;
    public int segPicked4;
    public Button BTNSig4;
    public Button BTNCancel4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer4);

        BTNSig4 = findViewById(R.id.btnSig4);
        BTNCancel4 = findViewById(R.id.btnCancel4);
        h4 = findViewById(R.id.numberPickerHora4);
        m4 = findViewById(R.id.numberPickerMinuto4);
        s4 = findViewById(R.id.numberPickerSegundo4);
        h4.setMinValue(0);
        h4.setMaxValue(99);
        m4.setMinValue(0);
        m4.setMaxValue(59);
        s4.setMinValue(0);
        s4.setMaxValue(59);

        BTNSig4.setEnabled(false);

        SharedPreferences preferences = this.getSharedPreferences("Archivo_Name4", Context.MODE_PRIVATE);
        String valor1 = preferences.getString("Name4","Add New" );
        if (valor1.equals("Add New")){
            BTNCancel4.setVisibility(View.INVISIBLE);
        }

        h4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hourPicked4 = newVal;
                if (newVal != 0){
                    BTNSig4.setEnabled(true);
                }
            }
        });

        m4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minPicked4 = newVal;
                if (newVal != 0){
                    BTNSig4.setEnabled(true);
                }
            }
        });

        s4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                segPicked4 = newVal;
                if (newVal != 0){
                    BTNSig4.setEnabled(true);
                }
            }
        });

        registerReceiver(MyBroadcast4, new IntentFilter("BROADCAST_TIMER4"));

    }

    public void btnCancel4(View view) {
        stopService(new Intent(Timer4.this, ServiceTimer4.class));
        long TIME_LIMIT = 0;
        SharedPreferences preferences = getSharedPreferences("Archivo_DataTimer4", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("Tiempo4", TIME_LIMIT);
        editor.apply();
        SharedPreferences preferences2 = getSharedPreferences("Archivo_Name4", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.putString("Name4", "Add New");
        editor2.apply();
        h4.setEnabled(true);
        m4.setEnabled(true);
        s4.setEnabled(true);
        h4.setValue(0);
        m4.setValue(0);
        s4.setValue(0);
    }

    private BroadcastReceiver MyBroadcast4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer4")) {
                    long millis = (intent.getLongExtra("timer4", 0));
                    long hrs = TimeUnit.MILLISECONDS.toHours(millis) % 99;
                    long min = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
                    long sec = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
                    h4.setEnabled(false);
                    m4.setEnabled(false);
                    s4.setEnabled(false);
                    h4.setValue((int) hrs);
                    m4.setValue((int) min);
                    s4.setValue((int) sec);
                }
            }
        }
    };

    public void btnSig4 (View view) {
        Intent i = new Intent(Timer4.this, MyApps4.class);
        long TIME_LIMIT = ((hourPicked4 * 1000 * 60 * 60) + (minPicked4 * 60 * 1000) + (segPicked4 * 1000));
        SharedPreferences preferences = getSharedPreferences("Archivo_DataTimer4", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("Tiempo4", TIME_LIMIT);
        editor.apply();
        startActivity(i);
    }



}