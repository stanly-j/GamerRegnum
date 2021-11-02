package com.example.gamerregnum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Referrals extends Fragment {

    String Code;
    String Point;
    String NameReferral;
    RecyclerView ListReferral;
    ListRecyclerAdapter adapter;
    TextView TextSeeMore;
    Button btn_copy;
    ArrayList<ListRecycler_Referrals> listDatos;

    public Referrals() {
        // Required empty public constructor
    }

    @Override   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_referrals, container, false);

        SharedPreferences preferences10 = getActivity().getSharedPreferences("ID", Context.MODE_PRIVATE);
        String UID = preferences10.getString("ID2","PtlYp2np93eoPsG0x89i66uy1VA3" );
        //Data base Firebase
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRootChild = mDatabaseReference.child("Cliente").child(UID);

        ListReferral = view.findViewById(R.id.List_Referral);
        TextSeeMore = view.findViewById(R.id.SeeMore);
        btn_copy = view.findViewById(R.id.btnCopy);
        ListReferral.setLayoutManager(new LinearLayoutManager(getActivity()));

        GetRefarrls();

        adapter = new ListRecyclerAdapter(listDatos);
        ListReferral.setAdapter(adapter);

        btn_copy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent Share = new Intent(android.content.Intent.ACTION_SEND);
                Share.setType("text/plain");
                Share.putExtra(android.content.Intent.EXTRA_SUBJECT, "Empleos Baja App");
                Share.putExtra(android.content.Intent.EXTRA_TEXT, Code);
                startActivity(Intent.createChooser(Share, "Share"));
            }});

        TextSeeMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for(int i= 0; i <= 100; i++) {
                    listDatos.add(new ListRecycler_Referrals(NameReferral, Point));
                }
                adapter.notifyDataSetChanged();
            }});

        mRootChild.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listDatos.clear();
                Code = dataSnapshot.child("code").getValue().toString();
                Point = dataSnapshot.child("puntos").getValue().toString();
                NameReferral = dataSnapshot.child("nombre").getValue().toString();
                for(int i= 0; i <= 4; i++) {
                    listDatos.add(new ListRecycler_Referrals(NameReferral, Point));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return view;
    }

    private ArrayList<ListRecycler_Referrals> GetRefarrls(){
        listDatos = new ArrayList<>();
        for(int i= 0; i <= 4; i++) {
            listDatos.add(new ListRecycler_Referrals(NameReferral, Point));
        }
        return listDatos;
    }

    public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.ViewHolderDatos>{

        ArrayList<ListRecycler_Referrals> listrecycler;

        public ListRecyclerAdapter(ArrayList<ListRecycler_Referrals> listrecycler) {
            this.listrecycler = listrecycler;
        }

        @NonNull
        @Override
        public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler_refarrals,null,false);
            return new ViewHolderDatos(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderDatos viewHolderDatos, int i) {
            viewHolderDatos.NameReferrals.setText(listrecycler.get(i).getNameReferrals());
            viewHolderDatos.PointReferrals.setText(listrecycler.get(i).getPointReferrals());
        }

        @Override
        public int getItemCount() {
            return listrecycler.size();
        }

        public class ViewHolderDatos extends RecyclerView.ViewHolder {

            TextView NameReferrals;
            TextView PointReferrals;

            public ViewHolderDatos(@NonNull View itemView) {
                super(itemView);
                NameReferrals = itemView.findViewById(R.id.idReferrals);
                PointReferrals = itemView.findViewById(R.id.idPoint);
            }
        }
    }

}