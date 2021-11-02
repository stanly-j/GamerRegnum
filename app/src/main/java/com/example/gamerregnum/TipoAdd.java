package com.example.gamerregnum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TipoAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_add);
    }

    public void ClickScanApps(View view) {
        startActivity(new Intent(this, ListApps.class));
    }

    public void ClickExternalApps(View view) {
        startActivity(new Intent(this, ExternalApps.class));
    }
}