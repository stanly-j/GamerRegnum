package com.example.gamerregnum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Others extends Fragment {

    private FirebaseAuth mAuth;

    public Others() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_others, container, false);

        Button btnFavoriteApps = view.findViewById(R.id.BTNFavoriteApps);
        Button btnLogout = view.findViewById(R.id.BTNLogout);

        btnFavoriteApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyFavoriteApps.class));
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                SharedPreferences preferences10 = getActivity().getSharedPreferences("ID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences10.edit();
                editor.putString("ID2", "0");
                editor.apply();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        return view;
    }
}