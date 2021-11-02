package com.example.gamerregnum;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class PagePrimary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_primary);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_tasks, R.id.navigation_referrals,
                R.id.navigation_rewards, R.id.navigation_others)
                .build();
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        //Cuando hay muchos datos que pasar favor utilizar
        //Bundle bundle = getIntent().getExtras();
        //bundle.getString("ID");

        /*String UID = getIntent().getExtras().getString("ID");
        Bundle args = new Bundle();
        args.putString("ID2", UID);

        Home fragment1= new Home();
        fragment1.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.navigation_home, fragment1).commit();

        Toast.makeText(this,"PagePrimary: "+UID, Toast.LENGTH_SHORT).show();*/

    }

}