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

public class MyApps2 extends AppCompatActivity {

    Button btnFinal2;
    ListView MyList2;
    ConexionSQLifeHelper conn;
    private ListAdacter3 adactador;
    String name2;

    private FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mRootChild = mDatabaseReference.child("Cliente").child("PtlYp2np93eoPsG0x89i66uy1VA3");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_apps2);

        btnFinal2 = findViewById(R.id.btnFinaly2);
        MyList2 = findViewById(R.id.MyListApps2);
        adactador = new ListAdacter3(this, GetArrayItemsDB2());
        MyList2.setAdapter(adactador);
        btnFinal2.setEnabled(false);

        MyList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                name2 = adactador.listStorage.get(i).getName();
                Toast.makeText(getApplicationContext(), "Selected: " + name2, Toast.LENGTH_SHORT).show();
                btnFinal2.setEnabled(true);
            }
        });

    }

    public void btnFinaly2(View view){
        Intent i = new Intent(this, PagePrimary.class);
        SharedPreferences preferences2 = getSharedPreferences("Archivo_Name2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences2.edit();
        editor.putString("Name2", name2);
        editor.apply();
        startService(new Intent(MyApps2.this, ServiceTimer2.class));
        startActivity(i);
    }

    private ArrayList<List_DB> GetArrayItemsDB2(){
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
            startActivity(new Intent(MyApps2.this, MyFavoriteApps.class));
            Toast.makeText(MyApps2.this, "Please add apps", Toast.LENGTH_LONG).show();
        }
        return listDbs;
    }

    public class ListAdacter3 extends BaseAdapter {
        private Context context;
        private ArrayList<List_DB> listStorage;

        public ListAdacter3(Context context, ArrayList<List_DB> listStorage) {
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