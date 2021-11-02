package com.example.gamerregnum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.concurrent.TimeUnit;


public class Home extends Fragment {

    TextView textRpoint;
    TextView textRreferrals;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    TextView textGamer1;
    TextView textGamer2;
    TextView textGamer3;
    TextView textGamer4;
    TextView textGamer5;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        textRpoint = view.findViewById(R.id.text_Rpoint);
        textRreferrals = view.findViewById(R.id.text_Rreferrals);
        text1 = view.findViewById(R.id.lbTimer1);
        text2 = view.findViewById(R.id.lbTimer2);
        text3 = view.findViewById(R.id.lbTimer3);
        text4 = view.findViewById(R.id.lbTimer4);
        text5 = view.findViewById(R.id.lbTimer5);
        textGamer1 = view.findViewById(R.id.text_gamer1);
        textGamer2 = view.findViewById(R.id.text_gamer2);
        textGamer3 = view.findViewById(R.id.text_gamer3);
        textGamer4 = view.findViewById(R.id.text_gamer4);
        textGamer5 = view.findViewById(R.id.text_gamer5);

        SharedPreferences preferences10 = getActivity().getSharedPreferences("ID", Context.MODE_PRIVATE);
        String UID = preferences10.getString("ID2","PtlYp2np93eoPsG0x89i66uy1VA3" );
        SharedPreferences preferences11 = getActivity().getSharedPreferences("Name", Context.MODE_PRIVATE);
        String Name = preferences11.getString("NameID","Name" );
        SharedPreferences preferences12 = getActivity().getSharedPreferences("Correo", Context.MODE_PRIVATE);
        String Correo = preferences12.getString("CorreoID","Email.@example.com" );

        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRootChild = mDatabaseReference.child("Cliente").child(UID);

        SharedPreferences preferences = getActivity().getSharedPreferences("Archivo_Name", Context.MODE_PRIVATE);
        String valor1 = preferences.getString("Name1","Add New" );
        textGamer1.setText(valor1);
        mRootChild.child("JuegoTime1").setValue(valor1);

        SharedPreferences preferences2 = getActivity().getSharedPreferences("Archivo_Name2", Context.MODE_PRIVATE);
        String valor2 = preferences2.getString("Name2","Add New" );
        textGamer2.setText(valor2);
        mRootChild.child("JuegoTime2").setValue(valor2);

        SharedPreferences preferences3 = getActivity().getSharedPreferences("Archivo_Name3", Context.MODE_PRIVATE);
        String valor3 = preferences3.getString("Name3","Add New" );
        textGamer3.setText(valor3);
        mRootChild.child("JuegoTime3").setValue(valor3);

        SharedPreferences preferences4 = getActivity().getSharedPreferences("Archivo_Name4", Context.MODE_PRIVATE);
        String valor4 = preferences4.getString("Name4","Add New" );
        textGamer4.setText(valor4);
        mRootChild.child("JuegoTime4").setValue(valor4);

        SharedPreferences preferences5 = getActivity().getSharedPreferences("Archivo_Name5", Context.MODE_PRIVATE);
        String valor5 = preferences5.getString("Name5","Add New" );
        textGamer5.setText(valor5);
        mRootChild.child("JuegoTime5").setValue(valor5);

        getActivity().registerReceiver(MyBroadcast, new IntentFilter("BROADCAST_TIMER1"));
        getActivity().registerReceiver(MyBroadcast2, new IntentFilter("BROADCAST_TIMER2"));
        getActivity().registerReceiver(MyBroadcast3, new IntentFilter("BROADCAST_TIMER3"));
        getActivity().registerReceiver(MyBroadcast4, new IntentFilter("BROADCAST_TIMER4"));
        getActivity().registerReceiver(MyBroadcast5, new IntentFilter("BROADCAST_TIMER5"));

        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent time1 = new Intent(getActivity(), Timer1.class);
                startActivity(time1);
            }
        });

        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent time2 = new Intent(getActivity(), Timer2.class);
                startActivity(time2);
            }
        });

        text3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent time3 = new Intent(getActivity(), Timer3.class);
                startActivity(time3);
            }
        });

        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent time4 = new Intent(getActivity(), Timer4.class);
                startActivity(time4);
            }
        });

        text5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent time5 = new Intent(getActivity(), Timer5.class);
                startActivity(time5);
            }
        });

        mRootChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String puntos = dataSnapshot.child("puntos").getValue().toString();
                textRpoint.setText(puntos);
                String referencias = dataSnapshot.child("referencias").getValue().toString();
                textRreferrals.setText(referencias);
                String JuegoTime1 = dataSnapshot.child("JuegoTime1").getValue().toString();
                textGamer1.setText(JuegoTime1);
                String JuegoTime2 = dataSnapshot.child("JuegoTime2").getValue().toString();
                textGamer2.setText(JuegoTime2);
                String JuegoTime3 = dataSnapshot.child("JuegoTime3").getValue().toString();
                textGamer3.setText(JuegoTime3);
                String JuegoTime4 = dataSnapshot.child("JuegoTime4").getValue().toString();
                textGamer4.setText(JuegoTime4);
                String JuegoTime5 = dataSnapshot.child("JuegoTime5").getValue().toString();
                textGamer5.setText(JuegoTime5);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return view;
    }

    private BroadcastReceiver MyBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer1")) {
                    long millis = (intent.getLongExtra("timer1", 0));
                    String timer = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                    text1.setText(timer);
                }
            }
        }
    };

    private BroadcastReceiver MyBroadcast2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer2")) {
                    long millis = (intent.getLongExtra("timer2", 0));
                    String timer2 = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                    text2.setText(timer2);
                }
            }
        }
    };

    private BroadcastReceiver MyBroadcast3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer3")) {
                    long millis = (intent.getLongExtra("timer3", 0));
                    String timer3 = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                    text3.setText(timer3);
                }
            }
        }
    };

    private BroadcastReceiver MyBroadcast4 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer4")) {
                    long millis = (intent.getLongExtra("timer4", 0));
                    String timer4 = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                    text4.setText(timer4);
                }
            }
        }
    };

    private BroadcastReceiver MyBroadcast5 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                if (intent.hasExtra("timer5")) {
                    long millis = (intent.getLongExtra("timer5", 0));
                    String timer5 = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                    text5.setText(timer5);
                }
            }
        }
    };


}