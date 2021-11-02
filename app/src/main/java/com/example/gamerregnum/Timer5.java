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

public class Timer5 extends AppCompatActivity {

    NumberPicker h5;
    NumberPicker m5;
    NumberPicker s5;
    public int hourPicked5;
    public int minPicked5;
    public int segPicked5;
    public Button BTNSig5;
    public Button BTNCancel5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer5);

        BTNSig5 = findViewById(R.id.btnSig5);
        BTNCancel5 = findViewById(R.id.btnCancel5);
        h5 = findViewById(R.id.numberPickerHora5);
        m5 = findViewById(R.id.numberPickerMinuto5);
        s5 = findViewById(R.id.numberPickerSegundo5);
        h5.setMinValue(0);
        h5.setMaxValue(99);
        m5.setMinValue(0);
        m5.setMaxValue(59);
        s5.setMinValue(0);
        s5.setMaxValue(59);

        BTNSig5.setEnabled(false);

        SharedPreferences preferences = this.getSharedPreferences("Archivo_Name5", Context.MODE_PRIVATE);
        String valor1 = preferences.getString("Name5","Add New" );
        if (valor1.equals("Add New")){
            BTNCancel5.setVisibility(View.INVISIBLE);
        }

        h5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hourPicked5 = newVal;
                if (newVal != 0){
                    BTNSig5.setEnabled(true);
                }
            }
        });

        m5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minPicked5 = newVal;
                if (newVal != 0){
                    BTNSig5.setEnabled(true);
                }
            }
        });

        s5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                segPicked5 = newVal;
                if (newVal != 0){
                    BTNSig5.setEnabled(true);
                }
            }
        });

        registerReceiver(MyBroadcast5, new IntentFilter("BROADCAST_TIMER5"));

    }

    public void btnCancel5(View view) {
        stopService(new Intent(Timer5.this, ServiceTimer5.class));
        long TIME_LIMIT = 0;
        SharedPreferences preferences = getSharedPreferences("Archivo_DataTimer5", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("Tiempo5", TIME_LIMIT);
        editor.apply();
        SharedPreferences preferences2 = getSharedPreferences("Archivo_Name5", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.putString("Name5", "Add New");
        editor2.apply();
        h5.setEnabled(true);
        m5.setEnabled(true);
        s5.setEnabled(true);
        h5.setValue(0);
        m5.setValue(0);
        s5.setValue(0);
    }

    private BroadcastReceiver MyBroadcast5 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer5")) {
                    long millis = (intent.getLongExtra("timer5", 0));
                    long hrs = TimeUnit.MILLISECONDS.toHours(millis) % 99;
                    long min = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;
                    long sec = TimeUnit.MILLISECONDS.toSeconds(millis) % 60;
                    h5.setEnabled(false);
                    m5.setEnabled(false);
                    s5.setEnabled(false);
                    h5.setValue((int) hrs);
                    m5.setValue((int) min);
                    s5.setValue((int) sec);
                }
            }
        }
    };

    public void btnSig5 (View view) {
        Intent i = new Intent(Timer5.this, MyApps5.class);
        long TIME_LIMIT = ((hourPicked5 * 1000 * 60 * 60) + (minPicked5 * 60 * 1000) + (segPicked5 * 1000));
        SharedPreferences preferences = getSharedPreferences("Archivo_DataTimer5", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("Tiempo5", TIME_LIMIT);
        editor.apply();
        startActivity(i);
    }

}