package com.example.gamerregnum;

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
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

public class Tasks extends Fragment {

    RecyclerView list_task;
    ListTask TaskAdactador;
    ArrayList<ItemTasks> ListDatos;
    Button Button;
    String Point = "50";
    String TitleTask = "Welcome Promo";

    public Tasks() {
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
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        list_task = view.findViewById(R.id.List_Task);
        list_task.setLayoutManager(new LinearLayoutManager(getActivity()));

        GetArrayTask();

        TaskAdactador = new ListTask(ListDatos);
        list_task.setAdapter(TaskAdactador);
        return view;
    }

    private ArrayList<ItemTasks> GetArrayTask() {
        ListDatos = new ArrayList<>();

        /*for(int i = 0; i<=4; i++){
            ListDatos.add(new ItemTasks(TitleTask, Point, Button ));
        }*/
        ListDatos.add(new ItemTasks(TitleTask, Point, Button ));

        return ListDatos;
    }

    public class ListTask extends RecyclerView.Adapter<ListTask.ViewHolder>{

        ArrayList<ItemTasks> listask;

        public ListTask(ArrayList<ItemTasks> listask) {
            this.listask = listask;
        }

        @NonNull
        @Override
        public ListTask.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_lisk_task, null,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ListTask.ViewHolder viewHolder, int i) {
            final int h = i;
            viewHolder.title.setText(listask.get(i).getTitleTask());
            viewHolder.point.setText(listask.get(i).getPointTask());
            viewHolder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( h == 0 ){
                        Toast.makeText(getActivity(), "Funcion 1", Toast.LENGTH_SHORT).show();
                    }
                    if ( h == 1 ){
                        Toast.makeText(getActivity(), "Funcion 2", Toast.LENGTH_SHORT).show();
                    }
                    if ( h == 2 ){
                        Toast.makeText(getActivity(), "Funcion 3", Toast.LENGTH_SHORT).show();
                    }
                    if ( h == 3 ){
                        Toast.makeText(getActivity(), "Funcion 4", Toast.LENGTH_SHORT).show();
                    }
                    if ( h == 4 ){
                        Toast.makeText(getActivity(), "Funcion 5", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return listask.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView point;
            Button button;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.text_TitleTask);
                point = itemView.findViewById(R.id.text_PointTask);
                button = itemView.findViewById(R.id.btn_Task);
            }
        }
    }


}