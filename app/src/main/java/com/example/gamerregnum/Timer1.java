package com.example.gamerregnum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.view.View;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Timer1 extends AppCompatActivity {

    NumberPicker h;
    NumberPicker m;
    NumberPicker s;
    public int hourPicked;
    public int minPicked;
    public int segPicked;
    public Button BTNSig;
    public Button BTNCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer1);

        BTNSig = findViewById(R.id.btnSig);
        BTNCancel = findViewById(R.id.btnCancel);
        h = findViewById(R.id.numberPickerHora);
        m = findViewById(R.id.numberPickerMinuto);
        s = findViewById(R.id.numberPickerSegundo);
        h.setMinValue(0);
        h.setMaxValue(99);
        m.setMinValue(0);
        m.setMaxValue(59);
        s.setMinValue(0);
        s.setMaxValue(59);

        BTNSig.setEnabled(false);

        SharedPreferences preferences = this.getSharedPreferences("Archivo_Name", Context.MODE_PRIVATE);
        String valor1 = preferences.getString("Name1","Add New" );
        if (valor1.equals("Add New")){
            BTNCancel.setVisibility(View.INVISIBLE);
        }

        h.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                hourPicked = newVal;
                if (newVal != 0){
                    BTNSig.setEnabled(true);
                }
            }
        });

        m.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                minPicked = newVal;
                if (newVal != 0){
                    BTNSig.setEnabled(true);
                }
            }
        });

        s.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                segPicked = newVal;
                if (newVal != 0){
                    BTNSig.setEnabled(true);
                }
            }
        });

        registerReceiver(MyBroadcast, new IntentFilter("BROADCAST_TIMER1"));

    }

    private BroadcastReceiver MyBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer1")) {
                    long millis = (intent.getLongExtra("timer1", 0));
                    long hrs = MILLISECONDS.toHours(millis) % 99;
                    long min = MILLISECONDS.toMinutes(millis) % 60;
                    long sec = MILLISECONDS.toSeconds(millis) % 60;
                    h.setEnabled(false);
                    m.setEnabled(false);
                    s.setEnabled(false);
                    h.setValue((int) hrs);
                    m.setValue((int) min);
                    s.setValue((int) sec);
                }
            }
        }
    };

    public void btnCancel (View view) {
        stopService(new Intent(Timer1.this, ServiceTimer.class));
        long TIME_LIMIT = 0;
        SharedPreferences preferences = getSharedPreferences("Archivo_DataTimer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("Tiempo1", TIME_LIMIT);
        editor.apply();
        SharedPreferences preferences2 = getSharedPreferences("Archivo_Name", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.putString("Name1", "Add New");
        editor2.apply();
        h.setEnabled(true);
        m.setEnabled(true);
        s.setEnabled(true);
        h.setValue(0);
        m.setValue(0);
        s.setValue(0);
    }

    public void btnSig (View view) {
        Intent i = new Intent(Timer1.this, MyApps.class);
        long TIME_LIMIT = ((hourPicked * 1000 * 60 * 60) + (minPicked * 60 * 1000) + (segPicked * 1000));
        SharedPreferences preferences = getSharedPreferences("Archivo_DataTimer", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("Tiempo1", TIME_LIMIT);
        editor.apply();
        startActivity(i);
    }

}