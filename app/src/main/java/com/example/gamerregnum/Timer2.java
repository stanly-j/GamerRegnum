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

public class Timer2 extends AppCompatActivity {

    NumberPicker h2;
    NumberPicker m2;
    NumberPicker s2;
    public int hourPicked2;
    public int minPicked2;
    public int segPicked2;
    public Button BTNSig2;
    public Button BTNCancel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer2);

        BTNSig2 = findViewById(R.id.btnSig2);
        BTNCancel2 = findViewById(R.id.btnCancel2);
        h2 = findViewById(R.id.numberPickerHora2);
        m2 = findViewById(R.id.numberPickerMinuto2);
        s2 = findViewById(R.id.numberPickerSegundo2);
        h2.setMinValue(0);
        h2.setMaxValue(99);
        m2.setMinValue(0);
        m2.setMaxValue(59);
        s2.setMinValue(0);
        s2.setMaxValue(59);

        BTNSig2.setEnabled(false);

        SharedPreferences preferences = this.getSharedPreferences("Archivo_Name2", Context.MODE_PRIVATE);
        String valor1 = preferences.getString("Name2","Add New" );
        if (valor1.equals("Add New")){
            BTNCancel2.setVisibility(View.INVISIBLE);
        }

        h2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hourPicked2 = newVal;
                if (newVal != 0){
                    BTNSig2.setEnabled(true);
                }
            }
        });

        m2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minPicked2 = newVal;
                if (newVal != 0){
                    BTNSig2.setEnabled(true);
                }
            }
        });

        s2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                segPicked2 = newVal;
                if (newVal != 0){
                    BTNSig2.setEnabled(true);
                }
            }
        });

        registerReceiver(MyBroadcast2, new IntentFilter("BROADCAST_TIMER2"));

    }

    private BroadcastReceiver MyBroadcast2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer2")) {
                    long millis = (intent.getLongExtra("timer2", 0));
                    long hrs = TimeUnit.MILLISECONDS.toHours(millis) % 99;
                    long min = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
                    long sec = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
                    h2.setEnabled(false);
                    m2.setEnabled(false);
                    s2.setEnabled(false);
                    h2.setValue((int) hrs);
                    m2.setValue((int) min);
                    s2.setValue((int) sec);
                }
            }
        }
    };

    public void btnCancel2 (View view) {
        stopService(new Intent(Timer2.this, ServiceTimer2.class));
        long TIME_LIMIT = 0;
        SharedPreferences preferences = getSharedPreferences("Archivo_DataTimer2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("Tiempo2", TIME_LIMIT);
        editor.apply();
        SharedPreferences preferences2 = getSharedPreferences("Archivo_Name2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.putString("Name2", "Add New");
        editor2.apply();
        h2.setEnabled(true);
        m2.setEnabled(true);
        s2.setEnabled(true);
        h2.setValue(0);
        m2.setValue(0);
        s2.setValue(0);
    }

    public void btnSig2 (View view) {
        Intent i = new Intent(Timer2.this, MyApps2.class);
        long TIME_LIMIT = ((hourPicked2 * 1000 * 60 * 60) + (minPicked2 * 60 * 1000) + (segPicked2 * 1000));
        SharedPreferences preferences = getSharedPreferences("Archivo_DataTimer2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("Tiempo2", TIME_LIMIT);
        editor.apply();
        startActivity(i);
    }

}