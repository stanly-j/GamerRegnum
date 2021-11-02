package com.example.gamerregnum;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamerregnum.Tablas.List_DB;
import com.example.gamerregnum.Utilidades.Utilidades;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class MyApps3 extends AppCompatActivity {

    Button btnFinal3;
    ListView MyList3;
    ConexionSQLifeHelper conn;
    private ListAdacter4 adactador;
    String name3;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRootChild = mDatabaseReference.child("Cliente").child("PtlYp2np93eoPsG0x89i66uy1VA3");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apps3);

        btnFinal3 = findViewById(R.id.btnFinaly3);
        MyList3 = findViewById(R.id.MyListApps3);
        adactador = new ListAdacter4(this, GetArrayItemsDB3());
        MyList3.setAdapter(adactador);
        btnFinal3.setEnabled(false);

        MyList3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                name3 = adactador.listStorage.get(i).getName();
                Toast.makeText(getApplicationContext(), "Selected: " + name3, Toast.LENGTH_SHORT).show();
                btnFinal3.setEnabled(true);
            }
        });

    }

    public void btnFinaly3(View view){
        Intent i = new Intent(this, PagePrimary.class);
        SharedPreferences preferences3 = getSharedPreferences("Archivo_Name3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences3.edit();
        editor.putString("Name3", name3);
        editor.apply();
        startService(new Intent(MyApps3.this, ServiceTimer3.class));
        startActivity(i);
    }

    private ArrayList<List_DB> GetArrayItemsDB3(){
        ArrayList<List_DB> listDbs = new ArrayList<>();
        conn = new ConexionSQLifeHelper(this, "db_listapps", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_NAME,null);
            while (cursor.moveToNext()){
                byte[] icon = cursor.getBlob(cursor.getColumnIndex("icon"));
                ByteArrayInputStream imageStream = new ByteArrayInputStream(icon);
                Bitmap theImage= BitmapFactory.decodeStream(imageStream);
                String name = cursor.getString(cursor.getColumnIndex("name"));
                listDbs.add(new List_DB(theImage, name));
            }
        }catch (Exception e){
            Toast.makeText(this, "Error no existe",Toast.LENGTH_SHORT).show();
        }
        if (listDbs.isEmpty()){
            startActivity(new Intent(MyApps3.this, MyFavoriteApps.class));
            Toast.makeText(MyApps3.this, "Please add apps", Toast.LENGTH_LONG).show();
        }
        return listDbs;
    }

    public class ListAdacter4 extends BaseAdapter {
        private Context context;
        private ArrayList<List_DB> listStorage;

        public ListAdacter4(Context context, ArrayList<List_DB> listStorage) {
            this.context = context;
            this.listStorage = listStorage;
        }

        @Override
        public int getCount() {
            return listStorage.size();
        }

        @Override
        public Object getItem(int i) {
            return listStorage.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            List_DB Item = (List_DB) getItem(i);
            view = LayoutInflater.from(context).inflate(R.layout.my_app_list,null);
            ImageView imagen = view.findViewById(R.id.icon_My_Apps);
            TextView Name = view.findViewById(R.id.list_my_app);
            Drawable d = new BitmapDrawable(getResources(), Item.getIcon());
            imagen.setImageDrawable(d);
            Name.setText(Item.getName());
            return view;
        }
    }

}